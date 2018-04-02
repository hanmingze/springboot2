package com.dljd.mail.constant;

/**
 * Created by jiaozhiguang on 2018/3/12.
 */

public enum MailStatus {

    //待发送
    DRAFT("0"),
    //发送到队列
    SEND_IN("1"),
    //发送成功
    NEED_OK("2"),
    //发送失败
    NEED_FAIL("3");

    private String code;

    MailStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
