package com.example.tygx.showAbstract;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.tygx.R;
import com.example.tygx.databinding.ActivityAbstractBinding;
import com.example.tygx.databinding.ActivityInputUrlBinding;
import com.example.tygx.showAbstract.adapter.ImageAdapter;
import com.example.tygx.showAbstract.bean.DataBean;
import com.example.tygx.utils.BaseActivity;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;


import butterknife.OnClick;

public class ShowAbstract extends BaseActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAbstractBinding inflate = ActivityAbstractBinding.inflate(getLayoutInflater());
        setContentView(inflate.getRoot());

        Intent intent = getIntent();

        inflate.toolbarAbstract.setNavigationOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            this.finish();
        });
        inflate.toolbarAbstract.setNavigationContentDescription("返回");
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        Banner banner = inflate.banner;
//        RichEditor mEditor = inflate.abstractEditor;
//        CoverFlowView coverFlowView = inflate.coverflow;

//        banner.addBannerLifecycleObserver(this)//添加生命周期观察者
//                .setAdapter(new ImageAdapter(DataBean.getTestData3()))
//                .setIndicator(new CircleIndicator(this));
        banner.setAdapter(new BannerImageAdapter<DataBean>(DataBean.getTestData()) {
            @Override
            public void onBindView(BannerImageHolder holder, DataBean data, int position, int size) {
                //图片加载自己实现
                Glide.with(holder.itemView)
                        .load(data.imageRes)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                        .into(holder.imageView);
            }
        })
                .addBannerLifecycleObserver(this)//添加生命周期观察者
                .setIndicator(new CircleIndicator(this));
    }

}

