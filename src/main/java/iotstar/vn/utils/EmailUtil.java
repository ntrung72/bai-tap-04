package iotstar.vn.utils;

import java.util.Properties;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailUtil{
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    public static boolean sendOtp(String toEmail, String otp, String purpose){
        String senderEmail = System.getenv("MAIL_USERNAME");
        String appPassword = System.getenv("MAIL_APP_PASSWORD");
        if(senderEmail == null || senderEmail.isBlank() || appPassword == null || appPassword.isBlank()){
            System.out.println("Chua cau hinh MAIL_USERNAME hoac MAIL_APP_PASSWORD trong Environment Variables.");
            return false;
        }
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);
        Session session = Session.getInstance(properties, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(senderEmail, appPassword);
            }
        });
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Ma OTP " + purpose);
            message.setText("Ma OTP cua ban la: " + otp + "\nMa co hieu luc trong 5 phut.\nKhong chia se ma nay voi bat ky ai.");
            Transport.send(message);
            return true;
        }catch(MessagingException e){
            e.printStackTrace();
            return false;
        }
    }
}