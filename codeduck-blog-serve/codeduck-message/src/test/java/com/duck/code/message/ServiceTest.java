package com.duck.code.message;

import com.duck.code.message.service.SendMailService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;


/**
 * @program: codeduck-blog-serve
 * @description:
 * @author: Code Duck
 * @create: 2021-01-10 14:54
 */
@SpringBootTest
public class ServiceTest {

    @Resource
    private SendMailService sendMailService;

    @Resource
    private TemplateEngine templateEngine;

    /**
     * 发送HTML邮件
     */
    @Test
    public void sendHtmlMail() {
        String content = "<html><body><h3><font color=\"red\">" + "大家好，这是springboot发送的HTML邮件" + "</font></h3></body></html>";
        sendMailService.sendHtmlMail("1281028036@qq.com", "发送邮件测试", content);
    }

    /**
     * 指定模板发送邮件
     */
    @Test
    public void testTemplateMail() {
        //向Thymeleaf模板传值，并解析成字符串
        Context context = new Context();
        context.setVariable("id", "token111111111");
        String emailContent = templateEngine.process("activeTemplate", context);

        sendMailService.sendHtmlMail("codeduck@163.com", "CodeDuck Blog账号激活", emailContent);
    }

}
