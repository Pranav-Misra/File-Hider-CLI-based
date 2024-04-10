package service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendOTPService {
    public static void sendOTP(String email, String genOTP) {
        String from = "pranavmisra1121@gmail.com";
        String host = "smtp.gmail.com";

        Properties properties = new Properties();

        // Use TLS
        properties.put("mail.smtp.starttls.enable", "true");
        // Set the host and port for Gmail
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "gvfirwndfpnogiqx");
            }
        });

        session.setDebug(true); // Enable debugging for more information

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email)); // Use the 'email' parameter
            message.setSubject("File Enc ka OTP");
            message.setText("Your One Time Password for file Enc app is " + genOTP); // Use 'genOTP' parameter

            System.out.println("Sending...");
            Transport.send(message);
            System.out.println("Sent message successfully...");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
