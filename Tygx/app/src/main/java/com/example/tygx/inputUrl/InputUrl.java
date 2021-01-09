package com.example.tygx.inputUrl;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import com.example.tygx.R;
import com.example.tygx.databinding.ActivityInputUrlBinding;
import com.example.tygx.databinding.ActivityLoginBinding;
import com.example.tygx.showAbstract.ShowAbstract;
import com.example.tygx.ui.login.LoginActivity;
import com.example.tygx.utils.BaseActivity;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
* 输入网页url以及关键词的页面
* 点击后进入摘要展示页面
*/
public class InputUrl extends BaseActivity {

    private static final int REQ_ABSTRACT = 1;

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

        inflate.buttonConfirmInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputUrl.this, ShowAbstract.class);
                startActivityForResult(intent, REQ_ABSTRACT);
            }
        });
    }


}

