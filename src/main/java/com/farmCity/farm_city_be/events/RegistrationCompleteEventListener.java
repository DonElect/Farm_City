package com.farmCity.farm_city_be.events;


import com.farmCity.farm_city_be.models.UserEntity;
import com.farmCity.farm_city_be.service.EmailServices;
import com.farmCity.farm_city_be.service.OTPService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final EmailServices emailServices;
    private UserEntity theUser;
    private final OTPService otpService;


    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        theUser = event.getUser();
        String otp = otpService.generateOTP(theUser.getEmail());
        emailServices.sendVerificationEmail(otp, theUser.getEmail(), theUser.getFirstName());
    }
}
