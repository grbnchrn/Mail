package com.mail.app.controller;

import com.mail.app.model.EmailDetails;
import com.mail.app.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/sendMail")
    public String sendMail(@RequestBody EmailDetails details){
        String status = emailService.sendSimpleMail(details);
        return status;
    }

    @PostMapping("/sendHtmlMail")
    public String sendHtmlMail(@RequestBody EmailDetails details){
        String status = emailService.sendHtmlMail(details);
        return status;
    }


}
