package com.example.tygx.myAbstract;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tygx.databinding.ActivityMyAbstractBinding;
import com.example.tygx.myAbstract.adapter.MyAbstractAdapter;
import com.example.tygx.utils.BaseActivity;
import com.google.android.material.tabs.TabLayoutMediator;

//我的摘要部分，可以查看已完成或处理中的列表
public class MyAbstract extends BaseActivity {

    ActivityMyAbstractBinding inflate;
    String[] tabs = {"未读", "已读"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflate = ActivityMyAbstractBinding.inflate(getLayoutInflater());
        setContentView(inflate.getRoot());

        Intent intent = getIntent();

        inflate.toolbarAbstract.setNavigationOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            this.finish();
        });
        inflate.toolbarAbstract.setNavigationContentDescription("返回");
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        inflate.viewPager.setAdapter(new MyAbstractAdapter(this));
        //预加载页数
        inflate.viewPager.setOffscreenPageLimit(1);
        //用户是否允许输入
        inflate.viewPager.setUserInputEnabled(true);

        new TabLayoutMediator(inflate.tabLayout, inflate.viewPager,
                (tab, position) -> tab.setText(tabs[position])
        ).attach();
    }


}


