package com.vicras.event;

import com.vicras.entity.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Locale;
import java.util.stream.Collectors;


@Getter
@EqualsAndHashCode
public class UserConfirmMessage {

    private final String baseUrl;
    private final User user;
    private final String templatePath;
    private final String code;
    private final String confirmLink;
    private final String subject;

    private UserConfirmMessage(User user, String template, String code, String confirmLink, String subject) {
        baseUrl = resolveBaseUrl();
        this.user = user;
        this.templatePath = template;
        this.code = code;
        this.confirmLink = confirmLink;
        this.subject = subject;
    }

    private String resolveBaseUrl() {
        try {
            return ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        } catch (IllegalStateException e) {
            return "";
        }
    }

    public static UserConfirmMessage getNewUserMessage(User user, String confirmLink, String code) {
        var subject = "Dealer Stat confirm message";
        String template = "classpath:templates/confirm-mail-template.html";
        return new UserConfirmMessage(user, template, code, confirmLink, subject);
    }

    public static UserConfirmMessage getPasswordResetUserMessage(User user, String resetLink, String code) {
        String subject = "Dealer Stat password reset message";
        String template = "classpath:templates/reset-mail-template.html";
        return new UserConfirmMessage(user, template, code, resetLink, subject);
    }

    public MimeMessage getMessage(JavaMailSender sender, TemplateEngine templateEngine) throws MessagingException, IOException {
        String body = getTemplateText(templateEngine);
        return configureMessage(body, sender);
    }

    private String getTemplateText(TemplateEngine templateEngine) throws IOException {
        Context context = new Context(Locale.getDefault());
        setTemplateVariablesFor(context);
        return getHtmlTextFrom(context, templateEngine);
    }

    private void setTemplateVariablesFor(Context ctx) {
        ctx.setVariable("firstName", user.getFirstName());
        ctx.setVariable("confirmLink", confirmLink);
        ctx.setVariable("code", code);
        ctx.setVariable("baseUrl", baseUrl);
    }


    private String getHtmlTextFrom(Context context, TemplateEngine templateEngine) throws IOException {
        return templateEngine.process(getFileTextAsString(), context);
    }

    private String getFileTextAsString() throws IOException {
        File file = ResourceUtils.getFile(templatePath);
        return Files.lines(file.toPath())
                .collect(Collectors.joining("\n"));
    }

    private MimeMessage configureMessage(String body, JavaMailSender sender) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        helper.setTo(user.getEmail());
        helper.setText(body, true);
        helper.setSubject(subject);

        return message;
    }

}
