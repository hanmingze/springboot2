package com.dljd.mail;

import com.dljd.mail.config.DBConfig;
import com.dljd.mail.constant.RedisPriorityQueue;
import com.dljd.mail.entity.MailSend;
import com.dljd.mail.entity.MstDict;
import com.dljd.mail.mapper.MailSendMapper;
import com.dljd.mail.mapper.MstDictMapper;
import com.dljd.mail.service.MailSendService;
import com.dljd.mail.service.MstDictService;
import com.dljd.mail.utils.FastJsonUtil;
import com.dljd.mail.utils.KeyUtil;
import com.github.pagehelper.PageHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Resource(name = "masterDataSource")
	private DataSource masterDataSource;

	@Resource(name = "slaveDataSource")
	private DataSource slaveDataSource;

	@Autowired
    private MstDictMapper mstDictMapper;

	@Autowired
	private MstDictService mstDictService;

	@Test
	public void testDataSource() throws Exception {
		Connection cm = masterDataSource.getConnection();
		System.out.println(cm.getMetaData().getURL());
		Connection cs = slaveDataSource.getConnection();
		System.out.println(cs.getMetaData().getURL());
	}

    @Test
    public void testMapper() {
        PageHelper.startPage(1, 2);
        List<MstDict> mstDicts = mstDictMapper.selectAll();
        for (MstDict md : mstDicts) {
            System.out.println(md.getName());
        }
    }

    @Test
    public void testFindByStatus() {

        List<MstDict> mstDicts= mstDictService.findByStatus("1");
        for (MstDict md : mstDicts) {
            System.out.println(md.getName() + " " + md.getStatus());
        }
    }

    @Autowired
    RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();

    @Test
    public void testRedis() {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set("a", "aaa");
        System.out.println(operations.get("a"));

        MailSend mailSend = getMailSend();

        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        listOperations.rightPush(RedisPriorityQueue.FAST_QUEUE.getCode(), FastJsonUtil.convertObjectToJSON(mailSend));

        String ret = listOperations.leftPop(RedisPriorityQueue.FAST_QUEUE.getCode());
        System.out.println(ret);

    }

    @Test
    public void testRedisList() {


        MailSend mailSend = getMailSend();

        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        listOperations.rightPush(RedisPriorityQueue.FAST_QUEUE.getCode(), FastJsonUtil.convertObjectToJSON(mailSend));

//        String ret = listOperations.leftPop(RedisPriorityQueue.FAST_QUEUE.getCode());
//        System.out.println(ret);

    }

    @Autowired
    private MailSendMapper mailSendMapper;

    @Test
    public void testSelectByPrimaryKey() {
        MailSend mailSend = mailSendMapper.selectByPrimaryKey("1");
        System.out.println(mailSend);
    }

    @Test
    public void testInsert() {
        MailSend mailSend = getMailSend();
        System.out.println(mailSend);
        mailSendMapper.insert(mailSend);
    }

    private MailSend getMailSend() {
        String id = KeyUtil.generatorUUID();
        int tabIndex = id.hashCode() % 2;
        MailSend mailSend = new  MailSend.Builder(id, "3")
                .sendContent("3").sendStatus("3").sendUserId("3").updateBy("3").version(0L).sendCount(0L)
                .sendPriority(3L).tabIndex(tabIndex).build();
        return mailSend;
    }

    @Test
    public void testUpdate() {
        MailSend mailSend = getMailSend();
        System.out.println(mailSend);
        mailSendMapper.updateByPrimaryKey(mailSend);
    }

    @Autowired
    private MailSendService mailSendService;


    @Autowired
    private DBConfig propertyConfig;

    @Test
    public void testTabCount() {
        System.out.println(propertyConfig.getMailTableCount());
    }





}
