package edu.uiowa.acm.judge.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * Created by Tom on 7/4/2015.
 */
@Service
public class EmailService {

    @Autowired
    private MailSender mailSender;

    @Value("${base.url}")
    private String baseUrl;

    public void sendConfirmationEmail(final String email, final int userId, final String uuid) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("iowa.acm@gmail.com");
        message.setTo(email);
        message.setSubject("Hawkeye Programming Challenge Account Confirmation");
        message.setText("Hello,\n" +
                "\tThank you for registering for the hawkeye programming challenge! \n" +
                "To complete your registration we just need you to confirm your email account.\n" +
                "To do so, please click the following link or paste it into your browser.\n" +
                baseUrl + "/user/confirm/" + userId + "/" + uuid + "\n\n" +
                "Thanks,\n" +
                "University of Iowa ACM");
        mailSender.send(message);
    }
}
