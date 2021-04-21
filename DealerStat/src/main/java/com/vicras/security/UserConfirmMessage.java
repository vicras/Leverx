package com.vicras.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.mail.SimpleMailMessage;


@Getter
@EqualsAndHashCode
public class UserConfirmMessage{
    private final SimpleMailMessage message;

    private UserConfirmMessage(String receiverMail, String subject, String message) {
        this.message = new SimpleMailMessage();
        this.message.setTo(receiverMail);
        this.message.setSubject(subject);
        this.message.setText(message);
    }

    public static UserConfirmMessage getNewUserMessage(String receiverMail, String confirmLink){
        String subject = "Dealer Stat confirm message";
        String message = "To confirm registration on the Dealer Stat site follow the link: " + confirmLink;
        return new UserConfirmMessage(receiverMail, subject, message);
    }

    public static UserConfirmMessage getPasswordResetUserMessage(String receiverMail, String resetLink, String code){
        String subject = "Dealer Stat password reset message";
        String message = "To reset password make POST query by follow link: " + resetLink;
        message += " with code: " + code + " and new password body";
        return new UserConfirmMessage(receiverMail, subject, message);
    }
}
