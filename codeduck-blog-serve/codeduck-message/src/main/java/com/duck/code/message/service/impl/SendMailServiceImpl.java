package com.duck.code.message.service.impl;

import com.duck.code.message.service.SendMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @program: codeduck-blog-serve
 * @description:
 * @author: Code Duck
 * @create: 2021-01-09 21:09
 */
@Slf4j
@Service
public class SendMailServiceImpl implements SendMailService {

    @Value("${spring.mail.username}")
    private String from;

    @Resource
    private JavaMailSender mailSender;


    @Override
    public void sendSimpleMail(String to, String subject, String content) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);              //收信人
        message.setSubject(subject);    //主题
        message.setText(content);       //内容
        message.setFrom(from);          //发信人

        mailSender.send(message);
    }
    @Override
    public void sendHtmlMail(String to, String subject, String content) {

        log.info("发送HTML邮件开始：{},{},{}", to, subject, content);
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);//true代表支持html
            mailSender.send(message);
            log.info("发送HTML邮件成功");
        } catch (MessagingException e) {
            log.error("发送HTML邮件失败：", e);
        }
    }
    @Override
    public void sendAttachmentMail(String to, String subject, String content, String filePath) {

        log.info("发送带附件邮件开始：{},{},{},{}", to, subject, content, filePath);
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = file.getFilename();
            helper.addAttachment(fileName, file);//添加附件，可多次调用该方法添加多个附件  
            mailSender.send(message);
            log.info("发送带附件邮件成功");
        } catch (MessagingException e) {
            log.error("发送带附件邮件失败", e);
        }


    }
    @Override
    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) {

        log.info("发送带图片邮件开始：{},{},{},{},{}", to, subject, content, rscPath, rscId);
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);//重复使用添加多个图片
            mailSender.send(message);
            log.info("发送带图片邮件成功");
        } catch (MessagingException e) {
            log.error("发送带图片邮件失败", e);
        }
    }
}
