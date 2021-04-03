package com.example.tygx.inputUrl;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import com.andreabaccega.formedittextvalidator.Validator;
import com.example.tygx.R;
import com.example.tygx.data.TaskListSharedPreference;
import com.example.tygx.data.repository.Abstract;
import com.example.tygx.data.repository.AbstractsManager;
import com.example.tygx.databinding.ActivityInputUrlBinding;
import com.example.tygx.databinding.ActivityLoginBinding;
import com.example.tygx.myAbstract.MyAbstract;
import com.example.tygx.request.GetTask;
import com.example.tygx.request.PostCreateTask;
import com.example.tygx.showAbstract.ShowAbstract;
import com.example.tygx.ui.login.LoginActivity;
import com.example.tygx.utils.BaseActivity;
import com.example.tygx.utils.Global;
import com.example.tygx.utils.Valid;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.installations.FirebaseInstallations;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/*
* 输入网页url以及关键词的页面
* 点击后进入摘要展示页面
*/
public class InputUrl extends BaseActivity {

    private static final int REQ_ABSTRACT = 1;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    /******************************
     ************ 回调 ************
     ******************************/
    private okhttp3.Callback createTask = new okhttp3.Callback() {
        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            try {
                if (response.code() != 200) {
                    InputUrl.this.runOnUiThread(() -> {
                        Toast toast = Toast.makeText(InputUrl.this, "url上传失败1", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP, 0, 0);
                        toast.show();});
                } else {
                    ResponseBody responseBody = response.body();
                    String responseBodyString = responseBody != null ? responseBody.string() : "";
                    if (Global.HTTP_DEBUG_MODE)
                        Log.e("HttpResponse", responseBodyString);
                    JSONObject jsonObject = new JSONObject(responseBodyString);
                    int status = (int) jsonObject.get("status");
                    String message = jsonObject.get("message").toString();
                    String result = jsonObject.get("result").toString();
                    JSONObject resultJson = new JSONObject(result);
                    String jobId = resultJson.get("job_id").toString();
                    //using sharedPreference
                    //TaskListSharedPreference.addUnreadTask(InputUrl.this,jobId);
                    //using Room
                    RequestBody requestBody= call.request().body();
                    Buffer buffer = new Buffer();
                    requestBody.writeTo(buffer);
                    Charset charset = StandardCharsets.UTF_8;
                    MediaType contentType = requestBody.contentType();
                    if (contentType != null) {
                        charset = contentType.charset(StandardCharsets.UTF_8);
                    }
                    String requestBodyString = buffer.readString(charset);

                    JSONObject requestJson = new JSONObject(requestBodyString);
                    String url = requestJson.get("url").toString();
                    Abstract newAbstract = new Abstract(jobId, url, "未读");
                    AbstractsManager.getIntance(InputUrl.this).abstractsDao().insert(newAbstract);

                    InputUrl.this.runOnUiThread(() -> {
                        Toast toast = Toast.makeText(InputUrl.this, message, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP, 0, 0);
                        toast.show();
                    });
                }
            } catch (JSONException e) {
                InputUrl.this.runOnUiThread(() -> {
                    Toast toast = Toast.makeText(InputUrl.this, "url上传失败2", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                });
                if (Global.HTTP_DEBUG_MODE)
                    Log.e("HttpResponse", e.toString());
            }
        }

        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {
            InputUrl.this.runOnUiThread(() -> {if (InputUrl.this.loadService != null)
                InputUrl.this.loadService.showSuccess();});
            InputUrl.this.runOnUiThread(() -> {
                Toast toast = Toast.makeText(InputUrl.this, "url上传失败3", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 0, 0);
                toast.show();});
            if (Global.HTTP_DEBUG_MODE)
                Log.e("HttpError", e.toString());
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityInputUrlBinding inflate = ActivityInputUrlBinding.inflate(getLayoutInflater());
        setContentView(inflate.getRoot());

        Intent intent = getIntent();
        //状态栏
        inflate.toolbarInputUrl.setNavigationOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            this.finish();
        });
        inflate.toolbarInputUrl.setNavigationContentDescription("返回");
        //沉浸式状态栏设置
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        Log.d("fID", "fID: "+ Global.fID);
        //自定义validator
        //inflate.textInputUrl.addValidator(new Valid.NotBlankValidator());
        inflate.buttonConfirmInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inflate.textInputUrl.testValidity()){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String url = inflate.textInputUrl.toString();
                                if (Global.HTTP_DEBUG_MODE) {
                                    url = "https://www.bilibili.com/video/BV1Bv411k745";
                                    Global.fID = "test";
                                }
//                                new GetTask(createTask, "667ecee481cec71cfc784457").send();
                                new PostCreateTask(createTask, url, Global.fID).sendJson();
                            } catch (Exception e) {
                                Log.e("exception", e.toString());
                            }
                        }
                    }).start();
                    Intent intent = new Intent(InputUrl.this, MyAbstract.class);
                    startActivityForResult(intent, REQ_ABSTRACT);
                }
            }
        });
    }


}

