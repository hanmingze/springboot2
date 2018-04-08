package com.dljd.mail.mapper;

import com.dljd.mail.entity.MailSend;

import java.util.Map;

public interface MailSendMapper {

    int deleteByPrimaryKey(String sendId);

    int insert(MailSend record);

    int insertSelective(MailSend record);

    MailSend selectByPrimaryKey(String sendId);

    int updateByPrimaryKeySelective(MailSend record);

    int updateByPrimaryKey(MailSend record);

    MailSend getMailSend(Map<String, String> map);

    int updateByPrimaryKeyAndVersion(MailSend record);

}