package com.dljd.mail.service;

import com.dljd.mail.DBConfig;
import com.dljd.mail.config.database.ReadOnlyAnnotation;
import com.dljd.mail.constant.MailStatus;
import com.dljd.mail.constant.RedisPriorityQueue;
import com.dljd.mail.entity.MailSend;
import com.dljd.mail.mapper.MailSendMapper;
import com.dljd.mail.utils.FastJsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiaozhiguang on 2018/3/12.
 */
@Component
public class MailSendService {

    public static final Logger LOGGER = LoggerFactory.getLogger(MailSendService.class);

    @Autowired
    private MailSendMapper mailSendMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private DBConfig dbConfig;

    public void insert(MailSend mailSend) {
        int hashCode = mailSend.getSendId().hashCode();
        mailSend.setTabIndex(hashCode % dbConfig.getMailTableCount());
        mailSendMapper.insert(mailSend);
    }

    public void sendRedis(MailSend mailSend) {

        Long priority = mailSend.getSendPriority();

        Long ret = 0L;
        Long size = 0L;

        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        if (priority < 4L) {
            //返回值是List的长度
            ret = listOperations.rightPush(RedisPriorityQueue.DEFER_QUEUE.getCode(), FastJsonUtil.convertObjectToJSON(mailSend));
            size = listOperations.size(RedisPriorityQueue.DEFER_QUEUE.getCode());
        } else if (priority < 7L) {
            ret = listOperations.rightPush(RedisPriorityQueue.NORMAL_QUEUE.getCode(), FastJsonUtil.convertObjectToJSON(mailSend));
            size = listOperations.size(RedisPriorityQueue.NORMAL_QUEUE.getCode());
        } else {
            ret = listOperations.rightPush(RedisPriorityQueue.FAST_QUEUE.getCode(), FastJsonUtil.convertObjectToJSON(mailSend));
            size = listOperations.size(RedisPriorityQueue.FAST_QUEUE.getCode());
        }
        //投递次数加1
        mailSend.setSendCount(mailSend.getSendCount() + 1);
        if (ret.equals(size)) {
            mailSend.setSendStatus(MailStatus.SEND_IN.getCode());
            mailSend.setTabIndex(mailSend.getSendId().hashCode() % dbConfig.getMailTableCount());
            mailSendMapper.updateByPrimaryKey(mailSend);
            LOGGER.info("-----进入队列成功 id: {}", mailSend.getSendId());
        } else {
            LOGGER.info("-----进入队列失败，等待重新投递 id: {}", mailSend.getSendId());
        }

    }

    @ReadOnlyAnnotation
    public List<MailSend> queryDraftList() {
        List<MailSend> list = new ArrayList<>();
        Map<String, String> map;
        for (int i = 0; i <dbConfig.getMailTableCount() ; i++) {
            map = new HashMap<>();
            map.put("tabIndex", i + "");
            list.addAll(mailSendMapper.queryDraftList(map));
        }
        return list;
    }


}
