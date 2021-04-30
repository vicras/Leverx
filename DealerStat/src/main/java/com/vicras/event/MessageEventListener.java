package com.vicras.event;

import com.vicras.exception.CannotSendMessageException;
import lombok.extern.log4j.Log4j;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Log4j
@Component
public class MessageEventListener {

    final JavaMailSender sender;

    final TemplateEngine templateEngine;

    public MessageEventListener(JavaMailSender sender, TemplateEngine templateEngine) {
        this.sender = sender;
        this.templateEngine = templateEngine;
    }

    @EventListener
    public void onApplicationEvent(UserConfirmMessage userMessage) {
        try {
            MimeMessage message = userMessage.getMessage(sender, templateEngine);
            sendMessage(message);
        } catch (IOException | MessagingException| RuntimeException e) {
            log.error("Error occurred while creating message for send, sending aborted", e);
            throw new CannotSendMessageException(e);
        }
    }

    private void sendMessage(MimeMessage message) {
        sender.send(message);
    }

}
