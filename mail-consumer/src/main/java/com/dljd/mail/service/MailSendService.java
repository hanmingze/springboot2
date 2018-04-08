package com.dljd.mail.service;

import com.dljd.mail.config.DBConfig;
import com.dljd.mail.constant.MailStatus;
import com.dljd.mail.entity.MailSend;
import com.dljd.mail.mapper.MailSendMapper;
import com.dljd.mail.vo.MailData;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiaozhiguang on 2018/3/12.
 */
@Service
public class MailSendService {

    public static final Logger LOGGER = LoggerFactory.getLogger(MailSendService.class);

    @Autowired
    private MailSendMapper mailSendMapper;

    @Autowired
    private DBConfig dbConfig;

    @Autowired
    private GeneratorMailTemplateHelper generatorMailTemplateHelper;

    public void sendMessage4Order(MailSend ms) {

        try {
//1 准备数据
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("userName", ms.getSendUserId());
            param.put("createDate", DateFormatUtils.format(ms.getUpdateTime(), "yyyy年MM月dd日"));
            param.put("exportUrl", "www.baidu.com");

            MailData data = new MailData();
            data.setTemplateName("SHEET");
            data.setUserId(ms.getSendUserId());
            data.setFrom("jiaozhiguang@126.com");
            data.setTo(ms.getSendTo());
            data.setSubject("【京东订单】");
            data.setParam(param);

            //2 模板渲染 和发送
            generatorMailTemplateHelper.generatorAndSend(data);

            //3 更新数据库状态 使用version乐观更新
            ms.setSendStatus(MailStatus.NEED_OK.getCode());
            mailSendMapper.updateByPrimaryKeyAndVersion(ms);

            LOGGER.info("发送邮件成功 id {}", ms.getSendId());

        } catch (Exception e) {
            e.printStackTrace();
            //count  5次
            if(ms.getSendCount() > 4) {
                ms.setSendStatus(MailStatus.NEED_FAIL.getCode());
                LOGGER.info("发送邮件失败， id : {}, userId" , ms.getSendId(), ms.getSendUserId());
            } else {
                ms.setSendStatus(MailStatus.DRAFT.getCode());
                LOGGER.info("发送邮件失败，等待重新发送， id : {}, userId" , ms.getSendId(), ms.getSendUserId());
            }

            int hashCode = ms.getSendId().hashCode();
            LOGGER.info("id {}", ms.getSendId());
            ms.setTabIndex(hashCode % dbConfig.getMailTableCount());
            mailSendMapper.updateByPrimaryKeyAndVersion(ms);

            throw new RuntimeException();
        }

    }
}
