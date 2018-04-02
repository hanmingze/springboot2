package com.dljd.mail.service.api;

import com.dljd.mail.constant.Const;
import com.dljd.mail.constant.MailStatus;
import com.dljd.mail.entity.MailSend;
import com.dljd.mail.service.MailSendService;
import com.dljd.mail.utils.KeyUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jiaozhiguang on 2018/3/12.
 */
@RestController
@RequestMapping(value = "/mailsend")
public class MailSendController {

    @Autowired
    private MailSendService mailSendService;

    @ApiOperation(value="邮件发送", notes="邮件发送")
    @ApiImplicitParam(name = "mailSend", value = "邮件发送实体", required = true, dataType = "MailSend")
    @PostMapping(value="")
    public MailSend send(@RequestBody MailSend mailSend) {

        //2 db
        mailSend.setSendId(KeyUtil.generatorUUID());
        mailSend.setSendCount(0L);
        mailSend.setSendStatus(MailStatus.DRAFT.getCode());
        mailSend.setVersion(0L);
        mailSend.setUpdateBy(Const.SYS_RUNTIME);
        mailSendService.insert(mailSend);

        //2 把数据发送到redis 并更新数据库状态
        mailSendService.sendRedis(mailSend);

        return mailSend;
    }

}
