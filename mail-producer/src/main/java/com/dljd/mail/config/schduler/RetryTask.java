package com.dljd.mail.config.schduler;

import com.dljd.mail.entity.MailSend;
import com.dljd.mail.service.MailSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jiaozhiguang on 2018/3/13.
 */
@Component
public class RetryTask {

    @Autowired
    private RedisTemplate<String, String > redisTemplate;

    @Autowired
    private MailSendService mailSendService;

    @Scheduled(initialDelay = 5000, fixedDelay = 10000)
    public void retry() {
        System.out.println("----------- run -------------");

        List<MailSend> list = mailSendService.queryDraftList();
        for (MailSend mailSend : list) {
            mailSendService.sendRedis(mailSend);
        }
    }

//    @Scheduled(initialDelay = 5000, fixedDelay = 1000 * 60 * 10)
//    public void intervalNormal() {
//        System.out.println("----------- run -------------");
//    }
//
//    @Scheduled(initialDelay = 5000, fixedDelay = 1000 * 60 * 60)
//    public void intervalDelay() {
//        System.out.println("----------- run -------------");
//    }

}
