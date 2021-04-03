package com.example.tygx.mainWindow;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.tygx.databinding.ActivityMainWindowBinding;
import com.example.tygx.inputUrl.InputUrl;
import com.example.tygx.myAbstract.MyAbstract;
import com.example.tygx.showAbstract.ShowAbstract;
import com.example.tygx.utils.BaseActivity;
import com.example.tygx.utils.Global;


/*
* 登录之后的主界面
* 可以选择开始，看历史摘要或者帮助
*/
public class MainWindow extends BaseActivity {

    private static final int REQ_ABSTRACT = 1;
    private static final int REQ_MY_ABSTRACT = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Global.HTTP_DEBUG_MODE){
            SharedPreferences sPreferences = this.getSharedPreferences("taskList", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sPreferences.edit();
            editor.clear();
            editor.apply();
        }

        ActivityMainWindowBinding inflate = ActivityMainWindowBinding.inflate(getLayoutInflater());
        setContentView(inflate.getRoot());

        Intent intent = getIntent();

        //一键开始
        inflate.buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.tygx.mainWindow.MainWindow.this, InputUrl.class);
                startActivityForResult(intent, REQ_ABSTRACT);
            }
        });

        //我的摘要
        inflate.buttonMyAbstract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.tygx.mainWindow.MainWindow.this, MyAbstract.class);
                startActivityForResult(intent, REQ_MY_ABSTRACT);
            }
        });

        //帮助说明
        inflate.buttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }


}


