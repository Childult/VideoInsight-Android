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
import com.google.android.material.tabs.TabLayoutMediator;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import com.example.tygx.showAbstract.adapter.TabAdapter;

//摘要内容展示页面
public class ShowAbstract extends BaseActivity {

    ActivityAbstractBinding inflate;
    String[] tabs = {"图文摘要", "文本摘要", "分段摘要", "视频摘要"};
    String jobId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         inflate = ActivityAbstractBinding.inflate(getLayoutInflater());
        setContentView(inflate.getRoot());

        Intent intent = getIntent();
        jobId = intent.getExtras().getString("jobId");

        inflate.toolbarAbstract.setNavigationOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            this.finish();
        });
        inflate.toolbarAbstract.setNavigationContentDescription("返回");
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        inflate.viewPager.setAdapter(new TabAdapter(this, jobId));
        //预加载页数
        inflate.viewPager.setOffscreenPageLimit(1);
        //用户是否允许输入
        inflate.viewPager.setUserInputEnabled(true);

        // 图片和摘要，迁移至fragment
//        Banner banner = inflate.banner;
//        banner.setAdapter(new BannerImageAdapter<DataBean>(DataBean.getTestData()) {
//            @Override
//            public void onBindView(BannerImageHolder holder, DataBean data, int position, int size) {
//                //图片加载自己实现
//                Glide.with(holder.itemView)
//                        .load(data.imageRes)
//                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
//                        .into(holder.imageView);
//            }
//        })
//                .addBannerLifecycleObserver(this)//添加生命周期观察者
//                .setIndicator(new CircleIndicator(this));

        //绑定tablayout和viewpager，设置tab
        new TabLayoutMediator(inflate.tabLayout, inflate.viewPager,
                (tab, position) -> tab.setText(tabs[position])
        ).attach();
    }


}

