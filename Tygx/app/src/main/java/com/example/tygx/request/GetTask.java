package com.example.tygx.request;

import com.example.tygx.request.base.BaseGetRequest;

import okhttp3.Callback;

public class GetTask extends BaseGetRequest {
    public GetTask(Callback callback, String jobId) {
        // 设置请求URL
        this.to("/job/"+jobId);
        // 设置回调函数
        this.call(callback);
    }
}
