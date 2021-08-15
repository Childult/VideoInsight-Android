package com.example.tygx.utils;

import androidx.annotation.NonNull;

public enum Status {
    // 创建任务
    START(0, "处理中"),
    CREATED(1, "处理中"),
    DOWNLOADED(2, "处理中"),

    // 任务成功
    DONE(3, "已完成"),

    // 任务失败
    RETRIEVE_ERR(4, "任务异常"),
    TEXT_ANALYSIS_ERR(5, "任务异常"),
    VIDEO_ANALYSIS_ERR(6, "任务异常"),
    BOTH_ANALYSIS_ERR(7, "任务异常");

    public final int statusCode;
    public final String statusMsg;

    private Status(int statusCode, String statusMsg) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
    }

    @NonNull
    @Override
    public String toString() {
        return this.statusMsg;
    }
}
