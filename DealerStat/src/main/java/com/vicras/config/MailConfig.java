package com.vicras.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:mail.properties")
public class MailConfig {

    private final Environment env;

    @Value("${sender.port}")
    private String SENDER_PORT;
    @Value("${sender.host}")
    private String SENDER_HOST;
    @Value("${sender.email.address}")
    private String SENDER_ADDRESS;
    @Value("${sender.email.pass}")
    private String SENDER_PASS;

    private final String DEBUG = "mail.debug";
    private final String SMTP_ENABLED = "mail.smtp.starttls.enable";
    private final String SMTP_AUTH = "mail.smtp.auth";
    private final String TRANSPORT_PROTOCOL = "mail.transport.protocol";
    private final String MAIL_TRUST = "mail.smtp.ssl.trust";

    public MailConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(SENDER_HOST);
        mailSender.setPort(Integer.parseInt(SENDER_PORT));

        mailSender.setUsername(SENDER_ADDRESS);
        mailSender.setPassword(SENDER_PASS);

        Properties props = mailSender.getJavaMailProperties();
        props.put(TRANSPORT_PROTOCOL, env.getProperty(TRANSPORT_PROTOCOL));
        props.put(SMTP_AUTH, env.getProperty(SMTP_AUTH));
        props.put(SMTP_ENABLED, env.getProperty(SMTP_ENABLED));
        props.put(DEBUG, env.getProperty(DEBUG));
        props.put(MAIL_TRUST, env.getProperty(MAIL_TRUST));

        return mailSender;
    }

}
