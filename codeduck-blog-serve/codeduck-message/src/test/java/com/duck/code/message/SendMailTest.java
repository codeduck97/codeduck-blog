package com.duck.code.message;

import com.duck.code.message.service.SendMailService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;

/**
 * @program: codeduck-blog-serve
 * @description:
 * @author: Code Duck
 * @create: 2021-01-09 21:05
 */
@SpringBootTest
public class SendMailTest {
    
    @Resource
    private SendMailService mailService;


    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 发送简单纯文本邮件
     */
    @org.junit.Test
    public void sendSimpleMail() {
        mailService.sendSimpleMail("123123123@qq.com", "发送邮件测试", "大家好，这是我用springboot进行发送邮件测试");
    }

    /**
     * 发送HTML邮件
     */
    @org.junit.Test
    public void sendHtmlMail() {
        String content = "<html><body><h3><font color=\"red\">" + "大家好，这是springboot发送的HTML邮件" + "</font></h3></body></html>";
        mailService.sendHtmlMail("123123123@qq.com", "发送邮件测试", content);
    }

    /**
     * 发送带附件的邮件
     */
    @org.junit.Test
    public void sendAttachmentMail() {
        String content = "<html><body><h3><font color=\"red\">" + "大家好，这是springboot发送的HTML邮件，有附件哦" + "</font></h3></body></html>";
        String filePath = "E:/docs/DSL查询.docx";
        mailService.sendAttachmentMail("123123123@qq.com", "发送邮件测试", content, filePath);
    }

    /**
     * 发送带图片的邮件
     */
    @org.junit.Test
    public void sendInlineResourceMail() {
        String rscPath = "E:/Jason_f/Picture_f/Wallpaper/325702.jpg";
        String rscId = "skill001";
        String content = "<html><body><h3><font color=\"red\">" + "大家好，这是springboot发送的HTML邮件，有图片哦" + "</font></h3>"
                + "<img src=\'cid:" + rscId + "\'></body></html>";
        mailService.sendInlineResourceMail("123123123@qq.com", "发送邮件测试", content, rscPath, rscId);
    }

    /**
     * 指定模板发送邮件
     */
    @Test
    public void testTemplateMail() {
        //向Thymeleaf模板传值，并解析成字符串
        Context context = new Context();
        context.setVariable("id", "tokenifadhkfkaljdsf");
        String emailContent = templateEngine.process("activeTemplate", context);

        mailService.sendHtmlMail("123123123@qq.com", "这是一个模板文件", emailContent);
    }

    
}
