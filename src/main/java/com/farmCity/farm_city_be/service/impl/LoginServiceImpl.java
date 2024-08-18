package com.farmCity.farm_city_be.service.impl;

import com.farmCity.farm_city_be.dtos.AuthResponse;
import com.farmCity.farm_city_be.dtos.LoginRequest;
import com.farmCity.farm_city_be.exception.InvalidPasswordException;
import com.farmCity.farm_city_be.exception.UserNotFoundException;
import com.farmCity.farm_city_be.exception.UserNotVerifiedException;
import com.farmCity.farm_city_be.models.UserEntity;
import com.farmCity.farm_city_be.repository.UserRepository;
import com.farmCity.farm_city_be.security.JWTGenerator;
import com.farmCity.farm_city_be.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;

    @Override
    public AuthResponse login(LoginRequest loginRequest, HttpServletRequest request) {

        UserEntity user = userRepo.findUserEntityByEmail(loginRequest.getEmail().toLowerCase())
                .orElseThrow(()-> new UserNotFoundException("Invalid login details."));

        if (!encoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new InvalidPasswordException("Invalid password!");
        }

        if (!user.isVerified()){
            throw new UserNotVerifiedException("Not verified!");
        }

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail().toLowerCase(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerator.generateToken(authentication, 120L);
        String freshToken = jwtGenerator.generateToken(authentication, 1440L);

        return new AuthResponse(token, freshToken, user.getPictureUrl());
    }

    @Override
    public void logout() {
        SecurityContextHolder.clearContext();
    }
}
