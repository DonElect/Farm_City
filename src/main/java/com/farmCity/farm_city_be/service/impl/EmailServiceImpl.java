package com.farmCity.farm_city_be.service.impl;

import com.farmCity.farm_city_be.exception.EmailNotSentException;
import com.farmCity.farm_city_be.service.EmailServices;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@RequiredArgsConstructor
@Component
public class EmailServiceImpl implements EmailServices {

    private final JavaMailSender mailSender;

    @Value("${default.email.sender}")
    private String defaultEmailSender;


    @Override
    public void sendSimpleMessage(String email, String subject, String message, String senderName)
            throws MessagingException,
            UnsupportedEncodingException {
        MimeMessage messageFormat = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(messageFormat);
        messageHelper.setFrom(defaultEmailSender, senderName);
        messageHelper.setTo(email);
        messageHelper.setSubject(subject);
        messageHelper.setText(message, true);
        mailSender.send(messageFormat);
    }

    @Override
    public void sendVerificationEmail(String otp, String email, String firstName) {
        String subject = "Email Verification";
        String senderName = "Farm City";
        String mailContent = "<p> Hi, " + firstName + ", </p>" +
                "<p>Thank you for registering with us.</p>" + "\n" +
                "<br>Please, enter the OTP below to complete your registration.</br> \n<br>This OTP <strong> expires in 5 minute</strong>.</br>" +
                "<h2>"+otp+"</h2>" +
                "<p> Thank you <br> Farm City Portal Service";

        new Thread(() -> {
            try {
                sendSimpleMessage(email, subject, mailContent, senderName);
            } catch (MessagingException | UnsupportedEncodingException e) {
                throw new EmailNotSentException("Email not sent.");
            }
        }).start();
    }
}
