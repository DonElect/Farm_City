package com.farmCity.farm_city_be.service;

import com.farmCity.farm_city_be.dtos.AuthResponse;
import com.farmCity.farm_city_be.dtos.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface LoginService {
    AuthResponse login(LoginRequest loginRequest, HttpServletRequest request);
    void logout();
}
