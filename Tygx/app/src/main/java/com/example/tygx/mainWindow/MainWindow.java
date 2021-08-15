package com.example.tygx.mainWindow;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.tygx.databinding.ActivityMainWindowBinding;
import com.example.tygx.inputUrl.InputUrl;
import com.example.tygx.myAbstract.MyAbstract;
import com.example.tygx.utils.BaseActivity;


/*
* 登录之后的主界面
* 可以选择开始，看历史摘要或者帮助
*/
public class MainWindow extends BaseActivity {

    private static final int REQ_ABSTRACT = 1;
    private static final int REQ_MY_ABSTRACT = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainWindowBinding inflate = ActivityMainWindowBinding.inflate(getLayoutInflater());
        setContentView(inflate.getRoot());

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);//设置statusbar为透明色
        getWindow().setNavigationBarColor(Color.TRANSPARENT);//设置NavigationBar为透明色

        //一键开始
        inflate.buttonStart.setOnClickListener(v -> {
            Intent intent = new Intent(MainWindow.this, InputUrl.class);
            startActivityForResult(intent, REQ_ABSTRACT);
        });

        //我的摘要
        inflate.buttonMyAbstract.setOnClickListener(v -> {
            Intent intent = new Intent(MainWindow.this, MyAbstract.class);
            startActivityForResult(intent, REQ_MY_ABSTRACT);
        });

        //帮助说明
        inflate.buttonHelp.setOnClickListener(v -> new AlertDialog.Builder(MainWindow.this)
                .setTitle("帮助说明")
                .setMessage("1.点击“一键开始”，输入视频URL提交后，等待数分钟即可获得图文摘要；\n" +
                        "2.点击“我的摘要”可查看摘要历史。")
                .setPositiveButton("好", null)
                .show());
    }
}


