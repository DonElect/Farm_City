package com.farmCity.farm_city_be.models;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OTPModel {
    private String otp;
    private LocalDateTime otpExpiryDate;
}
