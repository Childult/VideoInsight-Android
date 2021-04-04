package com.example.tygx.myAbstract.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.example.tygx.R;
import com.example.tygx.data.repository.Abstract;
import com.example.tygx.myAbstract.fragment.dummy.DummyContent.DummyItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Abstract}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyAbstractRecyclerViewAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder>  {

    private final List<T> mValues;
    protected View mHeaderView;
    protected View mFooterView;
    protected Context mContext;

    protected View mView;
    protected TextView mUrl;
    protected TextView mMessage;
    protected TextView mStatus;
    protected TextView mJobId;

    public MyAbstractRecyclerViewAdapter(List<T> items, Context context) {
        super(R.layout.fragment_done_abstract, items);
        this.mValues = items;
        this.mContext = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, T t) {
        initView(viewHolder, t);
        initData(viewHolder, t);
        setListener(viewHolder, t);

    }

    protected void initView(BaseViewHolder viewHolder, T t){


    }

    protected void initData(BaseViewHolder viewHolder, T t){

        Abstract data = (Abstract) t;
        viewHolder.setText(R.id.job_id,"jobId: "+data.getJobId());
        viewHolder.setText(R.id.url,"url: "+data.getUrl());
        viewHolder.setText(R.id.status,"status: "+data.getStatus());
        viewHolder.setText(R.id.message,"message: "+data.getMessage());

        mView = viewHolder.itemView;
        mJobId = viewHolder.getView(R.id.job_id);
        mUrl = viewHolder.getView(R.id.url);
        mStatus = viewHolder.getView(R.id.status);
        mMessage = viewHolder.getView(R.id.message);
    }

    protected void setListener(BaseViewHolder viewHolder, T t){

    }

    /**
     * 获取position，当添加有header或footer要注意改变
     **/
    public int getPosition(BaseViewHolder viewHolder) {
        return viewHolder.getLayoutPosition();
    }

    /**
     * 获取headerView
     **/
    protected View getHeaderView(int headerViewId) {
        if (mContext != null) {
            mHeaderView = LayoutInflater.from(mContext).inflate(headerViewId, null);
        }
        return mHeaderView;
    }

    /**
     * 获取footerView
     **/
    protected View getFooterView(int footerViewId) {
        if (mContext != null && mFooterView == null) {
            mFooterView = LayoutInflater.from(mContext).inflate(footerViewId, null);
        }
        return mFooterView;
    }

    /**
     * 添加headerView
     **/
    public void addHeaderView(int headerViewId) {
        addHeaderView(getHeaderView(headerViewId));
    }

    /**
     * 添加footerView
     **/
    public void addFooterView(int footerViewId) {
        addFooterView(getFooterView(footerViewId));
    }

    /**
     * 设置RecyclerView
     **/
    public void setRecyclerManager(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(this);
    }

    /**
     * adapter渐现动画
     **/
    public void openAlphaAnimation() {
        openLoadAnimation(BaseQuickAdapter.ALPHAIN);
    }

    /**
     * adapter缩放动画
     **/
    public void openScaleAnimation() {
        openLoadAnimation(BaseQuickAdapter.SCALEIN);
    }

    /**
     * adapter从下到上动画
     **/
    public void openBottomAnimation() {
        openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
    }

    /**
     * adapter从左到右动画
     **/
    public void openLeftAnimation() {
        openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
    }

    /**
     * adapter从右到左动画
     **/
    public void openRightAnimation() {
        openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
    }

    /**
     * 自定义动画
     **/
    public void openLoadAnimation(BaseAnimation animation) {

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

}