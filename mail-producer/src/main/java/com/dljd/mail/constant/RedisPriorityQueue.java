package com.dljd.mail.constant;

/**
 * Created by jiaozhiguang on 2018/3/12.
 */
public enum RedisPriorityQueue {

    //7，8，9隐私 安全 交易
    FAST_QUEUE("fast"),
    //4，5，6活动 通知
    NORMAL_QUEUE("normal"),
    //1，2，3汇总 调查
    DEFER_QUEUE("defer");

    private String code;

    RedisPriorityQueue(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
