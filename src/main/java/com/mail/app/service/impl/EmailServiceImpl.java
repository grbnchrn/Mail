package com.mail.app.service.impl;

import com.mail.app.model.EmailDetails;
import com.mail.app.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public String sendSimpleMail(EmailDetails details) {

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        } catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

    @Override
    public String sendHtmlMail(EmailDetails details) {
        try {
            getHtmlBody(details.getMsgBody());
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mailMessage = new MimeMessageHelper(message, true, "UTF-8");
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(getHtmlBody(details.getMsgBody()), true);
            mailMessage.setSubject(details.getSubject());
            javaMailSender.send(message);
            return "Mail Sent Successfully...";
        } catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

    private String getHtmlBody(String msgBody) {
        Context thymeleafContext = new Context();
        Map<String, Object> templateModel = new HashMap<String, Object>();
        templateModel.put("recipientName", "Sir/Madam");
        templateModel.put("text", msgBody);
        templateModel.put("senderName", "Team SpringBoot");
        thymeleafContext.setVariables(templateModel);
        return thymeleafTemplateEngine.process("template-thymeleaf.html", thymeleafContext);
    }
}
