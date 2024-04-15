package marc.dev.documentmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import marc.dev.documentmanagementsystem.exception.ApiException;
import marc.dev.documentmanagementsystem.service.EmailService;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static marc.dev.documentmanagementsystem.utils.EmailUtils.getEmailMessage;
import static marc.dev.documentmanagementsystem.utils.EmailUtils.getResetPasswordMessage;

@Service
@RequiredArgsConstructor
@Slf4j
@PropertySource("classpath:application.properties")
public class EmailServiceImpl implements EmailService {
    public static final String NEW_USER_ACCOUNT_VERIFICATION = "New User Account Verification";
    private static final String PASSWORD_RESET_REQUEST = "Password Reset Request";
    private final JavaMailSender sender;
    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromEmail;


    @Override
    @Async
    public void sendNewAccountEmail(String name, String email, String token) {
        try{
            log.info("Received REGISTRATION event for user {} with email {} and token {}", name, email,token);

            var message = new SimpleMailMessage();
            message.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            message.setFrom(fromEmail);
            message.setTo(email);
            message.setText(getEmailMessage(name, host, token));
            sender.send(message);
        }catch(Exception exception){
            log.error(exception.getMessage());
            throw new ApiException("enable to send a message");

        }

    }



    @Override
    @Async
    public void sendPasswordResetEmail(String name, String email, String token) {

        try{
            var message = new SimpleMailMessage();
            message.setSubject(PASSWORD_RESET_REQUEST);
            message.setFrom(fromEmail);
            message.setTo(email);
            message.setText(getResetPasswordMessage(name, host, token));
        }catch(Exception exception){
            log.error(exception.getMessage());
            throw new ApiException("enable to send a message");

        }

    }




}
