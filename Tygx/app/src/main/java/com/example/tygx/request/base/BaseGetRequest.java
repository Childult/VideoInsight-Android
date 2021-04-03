package com.example.tygx.request.base;

import com.example.tygx.utils.Http;
import com.example.tygx.utils.Valid;

import java.util.HashMap;

import okhttp3.Callback;

public class BaseGetRequest {
    private String url = null;                                  // 请求URL
    private HashMap<String, String> query = new HashMap<>();    // 请求参数
    private Callback callback = null;                           // 回调函数

    /**
     * 设置请求URL
     *
     * @param url {String} 请求URL
     */
    protected void to(String url) {
        this.url = url;
    }

    /**
     * 添加请求参数
     *
     * @param key   {String} 参数 键
     * @param value {String>} 参数 值
     */
    protected void put(String key, String value) {
        if (!Valid.isBlank(key) && !Valid.isBlank(value))
            this.query.put(key, value);
    }

    /**
     * 设置回调函数
     *
     * @param callback {Callback} 回调函数
     */
    protected void call(Callback callback) {
        this.callback = callback;
    }

    /**
     * 发送请求
     */
    public void send() {
        Http.sendHttpGetRequest(this.url, this.query, this.callback);
    }
}
