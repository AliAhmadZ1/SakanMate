package com.example.sakanmate.Service;

import com.example.sakanmate.Api.ApiException;
import com.example.sakanmate.DtoOut.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Service
public class EmailNotificationService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public void sendEmail(EmailRequest emailRequest){

        if (emailRequest==null)
            throw new ApiException("email null");
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(senderEmail);
        simpleMailMessage.setTo(emailRequest.getRecipient());
        simpleMailMessage.setText(emailRequest.getMessage());
        simpleMailMessage.setSubject(emailRequest.getSubject());
        simpleMailMessage.setSentDate(Date.from(Instant.now()));
        javaMailSender.send(simpleMailMessage);
    }

}
