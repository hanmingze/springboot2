package com.dljd.mail.config.schduler;

import com.dljd.mail.constant.RedisPriorityQueue;
import com.dljd.mail.service.MailSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by jiaozhiguang on 2018/3/13.
 */
@Component
public class ConsumerMailTask {

    @Autowired
    private RedisTemplate<String, String > redisTemplate;

    @Autowired
    private MailSendService mailSendService;

    @Scheduled(initialDelay = 5000, fixedDelay = 2000)
    public void intervalFast() {
        System.out.println("----------- run -------------");

        ListOperations<String, String> operations = redisTemplate.opsForList();

        String ret = operations.leftPop(RedisPriorityQueue.FAST_QUEUE.getCode());
        System.out.println(ret);

//        if (StringUtil.isNotEmpty(ret)) {
//            MailSend mailSend = FastJsonUtil.convertJSONToObject(ret, MailSend.class);
//            mailSendService.sendMessage4Order(mailSend);
//        }
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
