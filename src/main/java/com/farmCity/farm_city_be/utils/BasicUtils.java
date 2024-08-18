package com.farmCity.farm_city_be.utils;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class BasicUtils {
    public static String trackingIdGenerator(){
        Instant now = Instant.now();

        String raw = String.valueOf(now).replace("T", "")
                .replace(":", "")
                .replace("-","")
                .replace("Z","")
                .replace(".","").substring(4, (now.toString().length()-12));


        Random random = new Random();
        random.nextInt(65, 90);

        String un = String.valueOf(random.nextInt());
        un = un.length() > 4 ? un.substring(un.length() - 4) : un;

        Integer last4 = Integer.parseInt(un);

        char first = (char)(Integer.parseInt(now.toString().substring(2, 4))+42);
        String first4 = raw.substring(0, 4);
        String second4 = raw.substring(4, 8);
        Integer third4 = Integer.parseInt(raw.substring(8));

        String result = String.format("%s%4s%s%4s%s%04d%s%04d", first,
                first4, (char)random.nextInt(65, 90),
                second4, (char)random.nextInt(65, 90),
                third4, (char)random.nextInt(65, 90),
                last4);

        return result.length() > 18 ? result.substring(0, result.length()-3) : result;
    }

    public static String generateRandomUsername() {
        SecureRandom random = new SecureRandom();
        long minValue = Long.MIN_VALUE;
        long maxValue = Long.MAX_VALUE;
        return String.format("%015d",random.nextLong(minValue, maxValue)).replace("-", "").substring(0, 15);
    }

    public static String toDateString(LocalDateTime time){
        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }
}
