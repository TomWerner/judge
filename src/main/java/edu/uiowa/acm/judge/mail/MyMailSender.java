package edu.uiowa.acm.judge.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * Created by Tom on 7/4/2015.
 */
@Service
public class MyMailSender {

    @Autowired
    private MailSender mailSender;

    public void setMailSender() {
    }

    public void placeOrder() {

        // Do the business calculations...

        // Call the collaborators to persist the order...

        // Create a thread safe "copy" of the template message and customize it
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("tomwer3@gmail.com");
        msg.setFrom("test@test.com");
        msg.setText("Test message");
        try{
            this.mailSender.send(msg);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
    }
}
