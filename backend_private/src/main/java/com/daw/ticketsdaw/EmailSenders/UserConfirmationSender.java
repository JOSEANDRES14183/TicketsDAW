package com.daw.ticketsdaw.EmailSenders;

import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class UserConfirmationSender {

    @Autowired
    private JavaMailSender mailSender;

    public void sendMessage(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@ticketsdaw.me");
        message.setTo("joseandresreyessantiago1234@gmail.com");
        message.setSubject("Hola tickets");
        message.setText("texto");
        mailSender.send(message);
    }
}
