package com.cds.projectpulse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.cds.projectpulse.config.MailConfig;

@Service
public class EmailService {

    @Autowired
    private MailConfig mailConfig;
    
    @Value("${spring.mail.username}")                   
    private String emailId;
    
    @Value("${spring.mail.password}")
    private String password;

    public void sendEmail(String to, String subject, String body) {
        JavaMailSender mailSender = mailConfig.createJavaMailSender(emailId,password);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}

