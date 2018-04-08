package com.dljd.mail.vo;

import lombok.Data;

import java.util.Map;

/**
 * Created by jiaozhiguang on 2018/3/14.
 */
@Data
public class MailData {

    private String userId;
    private String subject;
    private String from;
    private String to;
    private String content;
    private String templateName;
    Map<String, Object> param;

}
