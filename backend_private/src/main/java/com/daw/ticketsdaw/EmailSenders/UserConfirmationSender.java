package com.daw.ticketsdaw.EmailSenders;

import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class UserConfirmationSender {

    @Autowired
    private JavaMailSender mailSender;

    public void sendMessage(String to, String userId) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,false);

        helper.setFrom("noreply@ticketsdaw.me");
        helper.setTo(to);
        helper.setSubject("Verifica tu cuenta");
        helper.setText("Haz click <a href='http://localhost:8080/auth/verify/"+ userId +"'>aqui</a> para verificar tu cuenta",true);

        mailSender.send(message);
    }
}