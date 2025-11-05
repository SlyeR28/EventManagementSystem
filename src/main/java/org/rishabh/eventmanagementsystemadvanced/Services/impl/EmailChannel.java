package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.User;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.ChannelType;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.NotificationRequest;
import org.rishabh.eventmanagementsystemadvanced.Repository.UserRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.NotificationChannel;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class EmailChannel implements NotificationChannel {

    private final JavaMailSender mailSender;
    private final UserRepository  userRepository;

    @Override
    public ChannelType channelType() {
        return ChannelType.EMAIL;
    }

    @Override
    public void send(NotificationRequest req) {

        String email = req.getUserEmail();

       if(req.getUserEmail()==null || req.getUserEmail().isBlank()){
           User user = userRepository.findById(req.getUserId())
                   .orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + req.getUserId()));

           if (user.getEmail() == null || user.getEmail().isBlank()) {
               throw new IllegalArgumentException("Email missing for userId: " + req.getUserId());
           }
           email = user.getEmail();
       }
       try{
           MimeMessage message = mailSender.createMimeMessage();
           MimeMessageHelper helper = new MimeMessageHelper(message, true);
           helper.setTo(req.getUserEmail());
           helper.setSubject(req.getSubject() != null ? req.getSubject() : "(no subject)");
           helper.setText(req.getMessage() != null ? req.getMessage() : " ", true);

           mailSender.send(message);
       }catch (MessagingException e){
           throw new RuntimeException("Error while sending email to " + req.getUserEmail(),e);
       }
    }
}
