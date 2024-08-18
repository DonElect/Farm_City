package com.farmCity.farm_city_be.service.impl;

import com.farmCity.farm_city_be.exception.InvalidOTPException;
import com.farmCity.farm_city_be.models.OTPModel;
import com.farmCity.farm_city_be.service.OTPService;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class OTPServiceImpl implements OTPService {
    private static final Map<String, OTPModel> otps = new ConcurrentHashMap<>();
    @Override
    public String generateOTP(String email) {
        SecureRandom random = new SecureRandom();
        String otp = random.nextInt(1000, 9999) + "";
        OTPModel model = OTPModel.builder()
                .otp(otp)
                .otpExpiryDate(LocalDateTime.now().plusMinutes(5))
                .build();
        otps.put(email, model);
        return otp;
    }

    @Override
    public boolean validateOTP(String otp, String email) {
        OTPModel model = otps.get(email);
        if (model == null || model.getOtpExpiryDate().isBefore(LocalDateTime.now())) {
            throw new InvalidOTPException("OTP has expired or is invalid.");
        }
        return model.getOtp().equals(otp);
    }

    @Scheduled(cron = "${remove.expired.otps.cron}")
    @SchedulerLock(name = "OTPRemovalScheduler", lockAtLeastFor = "1m", lockAtMostFor = "1m")
    private void removeExpiredOTP() {
        new Thread(()-> {
            otps
                    .entrySet()
                    .stream()
                    .filter(entry -> entry.getValue().getOtpExpiryDate().isBefore(LocalDateTime.now().minusMinutes(5)))
                    .forEach(entry -> otps.remove(entry.getKey()));
            log.info("Removed expired otps");
        }).start();
    }
}
