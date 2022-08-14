package com.mail.app.service;

import com.mail.app.model.EmailDetails;

public interface EmailService {

    public String sendSimpleMail(EmailDetails details);
    public String sendHtmlMail(EmailDetails details);
}
