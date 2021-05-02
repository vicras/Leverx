package com.vicras.event;

import com.vicras.exception.CannotSendMessageException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class MessageEventListenerTest {

    @Mock
    JavaMailSender mailSender;
    @Mock
    TemplateEngine templateEngine;

    @InjectMocks
    MessageEventListener eventListener;

    @Test
    void testMessageSending() {
        var message = Mockito.mock(UserConfirmMessage.class);
        eventListener.onApplicationEvent(message);

        Mockito.verify(mailSender, Mockito.times(1))
                .send((MimeMessage) isNull());
    }

    @Test
    void testMessageSendingFailure() throws MessagingException, IOException {
        var message = Mockito.mock(UserConfirmMessage.class);
        Mockito.doThrow(RuntimeException.class)
                .when(message)
                .getMessage(
                        eq(mailSender),
                        eq(templateEngine));
        Assertions.assertThrows(
                CannotSendMessageException.class, () ->
                        eventListener.onApplicationEvent(message)
        );
    }
}