package com.farmCity.farm_city_be.service;


public interface OTPService {
    String generateOTP(String email);
    boolean validateOTP(String otp, String email);
}
