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

public class InputUrl extends BaseActivity {

    private static final int REQ_ABSTRACT = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityInputUrlBinding inflate = ActivityInputUrlBinding.inflate(getLayoutInflater());
        setContentView(inflate.getRoot());

        Intent intent = getIntent();

        inflate.toolbarInputUrl.setNavigationOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            this.finish();
//            ActivityLoginBinding inflate1 = ActivityLoginBinding.inflate(getLayoutInflater());
//            setContentView(inflate1.getRoot());
//            inflate1.loading.setVisibility(View.INVISIBLE);
        });
        inflate.toolbarInputUrl.setNavigationContentDescription("返回");
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

