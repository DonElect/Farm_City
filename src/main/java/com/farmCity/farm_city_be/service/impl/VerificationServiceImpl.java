package com.farmCity.farm_city_be.service.impl;

import com.farmCity.farm_city_be.dtos.OTPVerificationRequest;
import com.farmCity.farm_city_be.exception.InvalidOTPException;
import com.farmCity.farm_city_be.exception.TokenExpirationException;
import com.farmCity.farm_city_be.exception.UserNotFoundException;
import com.farmCity.farm_city_be.models.UserEntity;
import com.farmCity.farm_city_be.repository.UserRepository;
import com.farmCity.farm_city_be.service.EmailServices;
import com.farmCity.farm_city_be.service.OTPService;
import com.farmCity.farm_city_be.service.VerificationServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VerificationServiceImpl implements VerificationServices {
    private final EmailServices emailServices;
    private final OTPService otpService;
    private final UserRepository userRepository;

    @Override
    public String verifyUserEmail(OTPVerificationRequest verificationRequest) {
        if(!verificationRequest.getOtp().matches("^\\d{6}$"))
            throw new InvalidOTPException("Invalid OTP");

        if (!otpService.validateOTP(verificationRequest.getOtp(), verificationRequest.getEmail())){
            throw new TokenExpirationException("OTP has expired");
        }

        UserEntity user = userRepository.findUserEntityByEmail(verificationRequest.getEmail())
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        user.setVerified(true);

        userRepository.save(user);

        return verificationRequest.getEmail();
    }

    @Override
    public void re_sendVerificationEmail(String email) {
        UserEntity user = userRepository.findUserEntityByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        String otp = otpService.generateOTP(user.getEmail());

        emailServices.sendVerificationEmail(otp, user.getEmail(), user.getFirstName());
    }
}
