package com.farmCity.farm_city_be.service;

import com.farmCity.farm_city_be.dtos.SignUpRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface SignupService {
    String register(SignUpRequest signUpRequest, HttpServletRequest request);
}
