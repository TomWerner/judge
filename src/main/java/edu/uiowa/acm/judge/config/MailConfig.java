package edu.uiowa.acm.judge.config;

import edu.uiowa.acm.judge.mail.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Created by Tom on 7/4/2015.
 */
@Configuration
public class MailConfig {

    @Value("${gmail.username}")
    private String username;

    @Value("${gmail.password}")
    private String password;

    @Bean
    public MailSender getMailSender() {
        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        final Properties prop = mailSender.getJavaMailProperties();
        prop.put("mail.transport.protocol", "smtp");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.debug", "true");

        return mailSender;
    }

    @Bean
    public EmailService getEmailService() {
        return new EmailService();
    }
}
