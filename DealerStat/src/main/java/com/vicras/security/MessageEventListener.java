package com.vicras.security;

import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MessageEventListener{

    final
    JavaMailSender sender;

    public MessageEventListener(JavaMailSender sender) {
        this.sender = sender;
    }

    @Async
    @EventListener
    public void onApplicationEvent(UserConfirmMessage userMessage) {
        var message = userMessage.getMessage();
        sendMessage(message);
    }

    private void sendMessage(SimpleMailMessage message){
//        sender.send(message);
    }

}
