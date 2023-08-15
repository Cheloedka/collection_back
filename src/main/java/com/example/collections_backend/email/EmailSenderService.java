package com.example.collections_backend.email;

import com.example.collections_backend.exception_handling.exceptions.EmailSendFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailSenderService {
     @Value("${spring.mail.username}")
     private String serverEmail;

     @Value("${domain.address}")
     private String domain;
     private final JavaMailSender mailSender;


     @Async
     public void sendEmail(String email, String title, String body) {
         try {
             MimeMessage message = mailSender.createMimeMessage();
             MimeMessageHelper helper = new MimeMessageHelper(message,"utf-8");

             helper.setText(body, true);
             helper.setTo(email);
             helper.setFrom(serverEmail);
             helper.setSubject(title);

             mailSender.send(message);
         }
         catch (MessagingException e) {
            throw new EmailSendFailedException();
         }

     }

    public void sendVerificationMail(String email, String name, String token)  {
        String body = EmailBody.verificationTemplate(name, domain + "/confirmation/" + token);
        sendEmail(email, "Verify your registration", body);
    }

    public void sendChangeEmailMail(String oldEmail, String newEmail, String name, String token) {
        String body = EmailBody.changeEmailTemplate(name, domain + "/resetMail/" + token, newEmail);
        sendEmail(oldEmail, "Change e-mail on new one", body);
    }

    public void sendNewEmailConfirmationMail(String email, String name, String token) {
        String body = EmailBody.changeEmailConfirmationNewTemplate(name, domain + "/newMail/" + token );
        sendEmail(email, "Confirm e-mail changing", body);
    }
}
