package com.farmCity.farm_city_be.service.impl;

import com.farmCity.farm_city_be.dtos.SignUpRequest;
import com.farmCity.farm_city_be.enums.Roles;
import com.farmCity.farm_city_be.events.RegistrationCompleteEvent;
import com.farmCity.farm_city_be.exception.DuplicateEmailException;
import com.farmCity.farm_city_be.exception.PasswordMismatchException;
import com.farmCity.farm_city_be.models.UserEntity;
import com.farmCity.farm_city_be.repository.UserRepository;
import com.farmCity.farm_city_be.service.SignupService;
import com.farmCity.farm_city_be.utils.BasicUtils;
import com.farmCity.farm_city_be.utils.ConstantUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SignupServiceImpl implements SignupService {
    private final UserRepository userRepos;
    private final PasswordEncoder encoder;
    private final ApplicationEventPublisher publisher;

    @Override
    public String register(SignUpRequest signUpRequest, HttpServletRequest request) {
        if (userRepos.existsByEmail(signUpRequest.getEmail().toLowerCase())) {
            throw new DuplicateEmailException("Email already exist!");
        }

        if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            throw new PasswordMismatchException("Password mismatch.");
        }
        Optional<UserEntity> superAdmin = userRepos.findFirstByRoles(Roles.SUPER_ADMIN);
        UserEntity user = UserEntity.builder()
                .username(superAdmin.isPresent() ? ConstantUtils.USER_PREFIX : ConstantUtils.SUPPER_ADMIN_PREFIX + BasicUtils.generateRandomUsername())
                .firstName(signUpRequest.getFirstName())
                .email(signUpRequest.getEmail().toLowerCase())
                .password(encoder.encode(signUpRequest.getPassword()))
                .confirmPassword(signUpRequest.getConfirmPassword())
                .roles(
                        superAdmin.isPresent() ? Roles.USER : Roles.SUPER_ADMIN
                )
                .isVerified(false)
                .build();
        userRepos.save(user);
        publisher.publishEvent(new RegistrationCompleteEvent(user));

        return "Verification link sent to Email. Check email and verify your account.";
    }
}
