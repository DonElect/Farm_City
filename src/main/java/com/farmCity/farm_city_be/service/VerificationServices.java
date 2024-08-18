package com.farmCity.farm_city_be.service;


import com.farmCity.farm_city_be.dtos.OTPVerificationRequest;

public interface VerificationServices {
    String verifyUserEmail(OTPVerificationRequest verificationRequest);
    void re_sendVerificationEmail(String email);
}
