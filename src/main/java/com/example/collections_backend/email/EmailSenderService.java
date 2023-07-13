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
     private String EmailToSend;

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
             helper.setFrom(EmailToSend);
             helper.setSubject(title);

             mailSender.send(message);
         }
         catch (MessagingException e) {
            throw new EmailSendFailedException();
         }

     }

     public void sendMail(String email, String name, String token){
         String body = EmailBody.confirmationMailBody(domain + "/confirmation/" + token, name);
         sendEmail(email, "Confirm your email" , body);
     }
}
