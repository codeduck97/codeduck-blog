package com.duck.code.message.listener;

import com.duck.code.message.service.SendMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @program: codeduck-blog-serve
 * @description: 邮件监听器【用于发送邮件】
 * @author: Code Duck
 * @create: 2021-01-09 21:06
 */
@Slf4j
@Component
public class MailListener {

    @Resource
    private SendMailService sendMailService;

    private String activeSubject = "CodeDuck Blog账号激活";

    @RabbitListener(queues = "codeduck.email")
    public void sendMail(Map<String, String> map) {
        if (map != null) {
            sendMailService.sendHtmlMail(
                    map.get("receiver"),
                    activeSubject,
                    map.get("text")
            );
        }
    }
}
