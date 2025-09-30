package com.gangadhar.portfolio.service;

import com.gangadhar.portfolio.model.Contact;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${portfolio.email.recipient}")
    private String recipientEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // --- 1. ADMIN NOTIFICATION ---
    public void sendNewContactMessage(Contact contact) {
        SimpleMailMessage message = new SimpleMailMessage();

        // Must match spring.mail.username in application.properties
        message.setFrom(recipientEmail);
        message.setTo(recipientEmail);
        message.setSubject("NEW PORTFOLIO CONTACT: " + contact.getName());

        String emailBody = String.format(
                "Name: %s\n" +
                        "Email: %s\n\n" +
                        "Message:\n%s",
                contact.getName(),
                contact.getEmail(),
                contact.getMessage()
        );

        message.setText(emailBody);
        mailSender.send(message);
    }

    // --- 2. USER AUTO-REPLY ---
    public void sendAutoReply(Contact contact) {
        SimpleMailMessage autoReply = new SimpleMailMessage();

        autoReply.setFrom(recipientEmail);
        autoReply.setTo(contact.getEmail()); // Send to the user's email

        autoReply.setSubject("Thanks for contacting Gangadhar Ingle!");

        String replyBody = String.format(
                "Dear %s,\n\n" +
                        "Thank you for reaching out to me! I have received your message and will review it shortly. I aim to reply to all inquiries within 24-48 hours.\n\n" +
                        "Here is a copy of the message you sent:\n" +
                        "------------------------------------\n" +
                        "%s\n" +
                        "------------------------------------\n\n" +
                        "Best Regards,\n" +
                        "Gangadhar Ingle",

                contact.getName(),
                contact.getMessage()
        );

        autoReply.setText(replyBody);
        mailSender.send(autoReply);
    }
}