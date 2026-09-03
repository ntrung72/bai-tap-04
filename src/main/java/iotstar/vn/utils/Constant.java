package iotstar.vn.utils;

public class Constant{
    public static final String SESSION_USERNAME = "username";
    public static final String COOKIE_REMEMBER = "username";
    public static final String DIR = "E:\\upload";
    public static final long OTP_EXPIRE_MILLIS = 5 * 60 * 1000;
    public static class Path{
        public static final String REGISTER = "/views/register.jsp";
        public static final String VERIFY_REGISTER = "/views/verify-register.jsp";
        public static final String FORGOT_PASSWORD = "/views/forgot-password.jsp";
        public static final String VERIFY_FORGOT_OTP = "/views/verify-forgot-otp.jsp";
        public static final String RESET_PASSWORD = "/views/reset-password.jsp";
    }
}