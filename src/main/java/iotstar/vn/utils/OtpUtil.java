package iotstar.vn.utils;

import java.security.SecureRandom;

public class OtpUtil{
    private static final SecureRandom RANDOM = new SecureRandom();
    public static String generateOtp(){
        return String.format("%06d", RANDOM.nextInt(1000000));
    }
}