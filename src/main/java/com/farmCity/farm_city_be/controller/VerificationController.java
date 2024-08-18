package com.farmCity.farm_city_be.controller;


import com.farmCity.farm_city_be.dtos.OTPVerificationRequest;
import com.farmCity.farm_city_be.models.ApiResponse;
import com.farmCity.farm_city_be.service.VerificationServices;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user-mgmt/otp")
public class VerificationController {
    private final VerificationServices verificationServices;

    @Operation(
            description = "Get end point for email_verification",
            summary = "Both admin,user and drivers will be able to verify Email",
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
    @PostMapping("/validate")
    public ResponseEntity<ApiResponse<String>> verifyOTP(@Valid @RequestBody OTPVerificationRequest request){
        return ResponseEntity.ok( new ApiResponse<>( "200","Successful", verificationServices.verifyUserEmail(request)));
    }

    @Operation(
            description = "Get end point for email confirmation",
            summary = "Both admin,user and drivers will be able to confirm email",
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
    @GetMapping("/resend")
    public ResponseEntity<ApiResponse<String>> emailReverification(@RequestParam("email") String email){
        verificationServices.re_sendVerificationEmail(email);
        return ResponseEntity.ok(new ApiResponse<>("200", "Successful", null));
    }
}
