package com.farmCity.farm_city_be.service;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface EmailServices {
    void sendSimpleMessage(String to, String subject, String text, String senderName) throws MessagingException, UnsupportedEncodingException;
    void sendVerificationEmail(String otp, String email, String firstName);
}