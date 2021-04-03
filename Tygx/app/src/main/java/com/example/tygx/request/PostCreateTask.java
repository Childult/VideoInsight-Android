package com.example.tygx.request;

import android.util.Log;

import com.example.tygx.request.base.BasePostRequest;
import com.example.tygx.utils.Global;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import okhttp3.Callback;

public class PostCreateTask extends BasePostRequest {
    public PostCreateTask(Callback callback, String url, String deviceID) {
        // 设置请求URL
        this.to("/job");
        // 设置json参数
        JSONObject json=new JSONObject();
        try{
            json.put("url", url);
            json.put("device_id", deviceID);
        }catch (JSONException e) {
            e.printStackTrace();
            Log.e("PostCreateTask", "PostCreateTask: 错误");
        }
        this.setJson(json.toString());
        if(Global.HTTP_DEBUG_MODE)
            Log.d("Json", "PostCreateTask: "+json.toString());
        // 设置回调函数
        this.call(callback);
    }
}
