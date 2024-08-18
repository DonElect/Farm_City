package com.farmCity.farm_city_be.controller;

import com.farmCity.farm_city_be.dtos.AuthResponse;
import com.farmCity.farm_city_be.dtos.LoginRequest;
import com.farmCity.farm_city_be.dtos.SignUpRequest;
import com.farmCity.farm_city_be.models.ApiResponse;
import com.farmCity.farm_city_be.service.LoginService;
import com.farmCity.farm_city_be.service.SignupService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user-mgmt")
public class UserSignupAndLoginController {
    private final SignupService signupService;
    private final LoginService loginService;

    @Operation(
            description = "Get end point for SignUp",
            summary = "user will be able to SignUp",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            description = "Success",
                            responseCode = "201"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> registration(@Valid @RequestBody SignUpRequest signUpRequest, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("201", "Successful", signupService.register(signUpRequest, request)));
    }

    @Operation(
            description = "Get end point for LogIn",
            summary = "user will be able to LogIn",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request){
        return ResponseEntity.ok(new ApiResponse<>("200", "Login successfully", loginService.login(loginRequest,request)));
    }

    @Operation(
            description = "Get end point for LogOut",
            summary = "user will be able to LogOut",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @GetMapping("/logout")
    private ResponseEntity<ApiResponse<String>> logout(){
        loginService.logout();
        return ResponseEntity.ok(new ApiResponse<>("200", "Logout Successfully", null));
    }
}
