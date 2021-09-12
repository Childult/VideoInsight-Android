package com.example.tygx.utils;

import com.example.tygx.snpe.Predictor;

public class Global {
    //    public static final String SERVER_URL = "http://192.168.2.80:6666/debug";
    public static final String SERVER_URL = "http://192.168.2.80:6666";
    //public static final String SERVER_URL = "http://[fe80::80aa:a342:5160:7340]:6666";
//    public static final String SERVER_URL = "http://[2400:dd01:100f:4f32:245d:ae54:f5dc:26db]:6666";
    public static final boolean HTTP_DEBUG_MODE = true;
    public static final boolean HTTP_TEST_MODE = true;
    public static String fID = "";
    // 轮询后端任务进度的时间间隔，单位为毫秒
    public static final int POLLING_INTERVAL = 5000;
    public static final String CHANNEL_ID = "com.example.tygx.notification";

    public static Predictor SNPE;
    public static final String[] HISTORY = {
            "https://b23.tv/uqSfgu",
            "https://b23.tv/uqSfgu, 考点",
            "https://b23.tv/5C1ylJ",
            "https://b23.tv/5C1ylJ, 无监督学习",
    };
}

