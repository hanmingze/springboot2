package com.dljd.mail;

import com.dljd.mail.service.MailService;
import com.dljd.mail.utils.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testSimpleMail() throws Exception {
        mailService.sendSimpleMail("jiaozhiguang@126.com","client simple mail"," hello this is simple mail");
    }

    @Test
    public void testHtmlMail() throws Exception {
        String content="<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封html邮件!</h3>\n" +
                "</body>\n" +
                "</html>";
        mailService.sendHtmlMail("jiaozhiguang@126.com","client simple mail",content);
    }

    @Test
    public void sendAttachmentsMail() {
        String filePath="/Users/jiaozhiguang/hello.txt";
        mailService.sendAttachmentsMail("jiaozhiguang@126.com", "主题：带附件的邮件", "有附件，请查收！", filePath);
    }


    @Test
    public void sendInlineResourceMail() {
        String rscId = "neo006";
        String content="<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
        String imgPath = "/Users/jiaozhiguang/a.png";

        mailService.sendInlineResourceMail("jiaozhiguang@126.com", "主题：这是有图片的邮件", content, imgPath, rscId);
    }


    @Test
    public void sendTemplateMail() {
        //创建邮件正文
        Context context = new Context();
        context.setVariable("id", "006");
        String emailContent = templateEngine.process("emailTemplate", context);

        mailService.sendHtmlMail("jiaozhiguang@126.com","主题：这是模板邮件",emailContent);
    }

    @Test
    public void sendTemplateMail2() {

        Map<String, Object> params = new HashMap<>();
        params.put("userName", "jiao");
        params.put("createDate", DateUtils.format(new Date(), "yyyy年MM月dd日"));
        params.put("exportUrl", "www.baidu.com");

        //创建邮件正文
        Context context = new Context();
        context.setVariables(params);
        String emailContent = templateEngine.process("SHEET", context);

        mailService.sendHtmlMail("jiaozhiguang@126.com","主题：这是模板邮件",emailContent);
    }
}