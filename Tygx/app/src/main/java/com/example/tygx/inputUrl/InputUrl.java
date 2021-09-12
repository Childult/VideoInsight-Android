package com.example.tygx.inputUrl;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListPopupWindow;
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
import com.example.tygx.utils.Status;
import com.example.tygx.utils.Valid;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.installations.FirebaseInstallations;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    protected Activity mActivity = InputUrl.this;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    /******************************
     ************ 回调 ************
     ******************************/
    private final okhttp3.Callback createTask = new okhttp3.Callback() {
        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            try {
                if (response.code() != 200) {
                    InputUrl.this.runOnUiThread(() -> {
                        Toast toast = Toast.makeText(InputUrl.this, "服务异常，请重试", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP, 0, 0);
                        toast.show();
                    });
                } else {
                    ResponseBody responseBody = response.body();
                    String responseBodyString = responseBody != null ? responseBody.string() : "";
                    if (Global.HTTP_DEBUG_MODE)
                        Log.e("HttpResponse", responseBodyString);
                    JSONObject jsonObject = new JSONObject(responseBodyString);
                    Status status = Status.values()[(int) jsonObject.get("status")];
                    String message = status.toString();
                    String result = jsonObject.get("result").toString();
                    JSONObject resultJson = new JSONObject(result);
                    String jobId = resultJson.get("job_id").toString();
                    //using sharedPreference
                    //TaskListSharedPreference.addUnreadTask(InputUrl.this,jobId);
                    //using Room
                    RequestBody requestBody = call.request().body();
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
                    String[] kws_arr;
                    Abstract newAbstract;
                    if (requestJson.has("keywords")) {
                        JSONArray arr = requestJson.getJSONArray("keywords");
                        kws_arr = new String[arr.length()];
                        for (int i = 0; i < arr.length(); i++) {
                            kws_arr[i] = arr.get(i).toString();
                        }
                        String kws = String.join(" ", kws_arr);
                        newAbstract = new Abstract(jobId, url, "未读", kws);
                    } else {
                        newAbstract = new Abstract(jobId, url, "未读");
                    }

                    AbstractsManager.getIntance(InputUrl.this).abstractsDao().insert(newAbstract);

                    InputUrl.this.runOnUiThread(() -> {
                        Toast toast = Toast.makeText(InputUrl.this, message, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP, 0, 0);
                        toast.show();
                    });

                    Intent intent = new Intent(InputUrl.this, MyAbstract.class);
                    startActivityForResult(intent, REQ_ABSTRACT);
                }
            } catch (JSONException e) {
                InputUrl.this.runOnUiThread(() -> {
                    Toast toast = Toast.makeText(InputUrl.this, "创建任务失败", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                });
                if (Global.HTTP_DEBUG_MODE)
                    Log.e("HttpResponse", e.toString());
            }
        }

        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {
            InputUrl.this.runOnUiThread(() -> {
                if (InputUrl.this.loadService != null)
                    InputUrl.this.loadService.showSuccess();
            });
            InputUrl.this.runOnUiThread(() -> {
                Toast toast = Toast.makeText(InputUrl.this, "创建任务失败，请检查网络设置", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 0, 0);
                toast.show();
            });
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
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);//设置statusbar为透明色
        getWindow().setNavigationBarColor(Color.TRANSPARENT);//设置NavigationBar为透明色

        Log.d("fID", "fID: " + Global.fID);
        inflate.buttonConfirmInput.setOnClickListener(v -> {
            String url = Objects.requireNonNull(inflate.textInputUrl.getText()).toString().trim();
            String[] keywords = Objects.requireNonNull(inflate.textInputKeyword.getText()).toString().trim().split(" ");

            if (url.length() == 0) {
                Toast toast = Toast.makeText(InputUrl.this, "URL不能为空！", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 0, 0);
                toast.show();
                return;
            }

            Pattern p = Pattern.compile("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()!@:%_\\+.~#?&\\/\\/=]*)",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            Matcher m = p.matcher(url);
            if (!m.find()) {
                Toast toast = Toast.makeText(InputUrl.this, "请输入有效的URL", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 0, 0);
                toast.show();
                return;
            }

            url = m.group();
            Log.d("input", "matched URL: " + url);
            inflate.textInputUrl.setText(url);
            String finalUrl = url;
            new Thread(() -> {
                try {
                    Log.d("FID:", Global.fID);
                    new PostCreateTask(createTask, finalUrl, Global.fID, keywords).sendJson();
                } catch (Exception e) {
                    Log.e("exception", e.toString());
                }
            }).start();

        });

        // show url history
        inflate.buttonUrlHistory.setOnClickListener(v -> {
            final ListPopupWindow listPopupWindow;
            listPopupWindow = new ListPopupWindow(mActivity);
            listPopupWindow.setAdapter(new ArrayAdapter<>(mActivity, android.R.layout.simple_list_item_1, Global.HISTORY));//用android内置布局，或设计自己的样式
            listPopupWindow.setAnchorView(inflate.textInputUrl);          //以哪个控件为基准，在该处以mEditText为基准
            listPopupWindow.setModal(true);

            // 设置项点击监听
            listPopupWindow.setOnItemClickListener((adapterView, view, i, l) -> {
                String[] tmp = Global.HISTORY[i].split(", ");
                if (tmp.length == 1) {
                    inflate.textInputUrl.setText(Global.HISTORY[i]);
                    inflate.textInputKeyword.setText("");
                } else if (tmp.length == 2) {
                    inflate.textInputUrl.setText(tmp[0]);
                    inflate.textInputKeyword.setText(tmp[1]);
                }

                listPopupWindow.dismiss();                             //如果已经选择了，隐藏起来
            });
            listPopupWindow.show();
        });
    }
}

