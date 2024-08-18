package com.farmCity.farm_city_be.dtos;

import com.farmCity.farm_city_be.utils.validator.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    @Size(min = 3, max = 35, message = "First name is too long or short")
    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @Size(max = 100, message = "Email is too long")
    @Email(message = "Must be a valid email")
    @NotBlank(message = "Email should not be blank!")
    private String email;

    @ValidPassword
    private String password;

    @Size(min = 4, max = 25, message = "Confirm Password too short or long")
    @NotBlank(message = "Confirm Password cannot be blank!")
    private String confirmPassword;
}
