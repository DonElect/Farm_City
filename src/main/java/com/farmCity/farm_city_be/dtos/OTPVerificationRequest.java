package com.farmCity.farm_city_be.dtos;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OTPVerificationRequest {
    @Email(message = "Invalid email provided")
    private String email;
    private String otp;
}
