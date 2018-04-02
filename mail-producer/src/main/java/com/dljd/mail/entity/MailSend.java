package com.dljd.mail.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class MailSend implements Serializable {

    private String sendId;

    private String sendTo;

    private String sendUserId;

    private String sendContent;

    private Long sendPriority;

    private Long sendCount;

    private String sendStatus;

    private String remark;

    private Long version;

    private String updateBy;

    private Date updateTime;

    //
    private int tabIndex;

    public static class Builder {

        MailSend target;

        public Builder(String sendId, String sendTo) {
            target = new MailSend();
            target.sendId = sendId;
            target.sendTo = sendTo;
        }

        public Builder sendUserId(String sendUserId) {
            target.sendUserId = sendUserId;
            return this;
        }

        public Builder sendContent(String sendContent) {
            target.sendContent = sendContent;
            return this;
        }

        public Builder sendPriority(Long sendPriority) {
            target.sendPriority = sendPriority;
            return this;
        }

        public Builder sendCount(Long sendCount) {
            target.sendCount = sendCount;
            return this;
        }

        public Builder sendStatus(String sendStatus) {
            target.sendStatus = sendStatus;
            return this;
        }

        public Builder version(Long version) {
            target.version = version;
            return this;
        }

        public Builder updateBy(String updateBy) {
            target.updateBy = updateBy;
            return this;
        }

        public Builder remark(String remark) {
            target.remark = remark;
            return this;
        }

        public Builder tabIndex(int tabIndex) {
            target.tabIndex = tabIndex;
            return this;
        }

        public MailSend build() {
            return new MailSend(target);
        }

    }

    // 构造器尽量缩小范围
    private MailSend() {

    }

    private MailSend(MailSend builder) {
        this.sendId = builder.sendId;
        this.sendTo = builder.sendTo;
        this.sendUserId = builder.sendUserId;
        this.sendContent = builder.sendContent;
        this.sendPriority = builder.sendPriority;
        this.sendCount = builder.sendCount;
        this.sendStatus = builder.sendStatus;
        this.version = builder.version;
        this.updateBy = builder.updateBy;
        this.remark = builder.remark;

    }

}