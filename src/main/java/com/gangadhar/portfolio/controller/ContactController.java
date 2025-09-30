package com.gangadhar.portfolio.controller;

import com.gangadhar.portfolio.model.Contact;
import com.gangadhar.portfolio.repository.ContactRepository;
import com.gangadhar.portfolio.service.EmailService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactRepository contactRepository;
    private final EmailService emailService;

    public ContactController(ContactRepository contactRepository, EmailService emailService) {
        this.contactRepository = contactRepository;
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<String> submitContact(@RequestBody Contact contact) {

        contactRepository.save(contact);

        try {
            // 1. Send notification to admin (you)
            emailService.sendNewContactMessage(contact);
            // 2. Send auto-reply to user
            emailService.sendAutoReply(contact);

            return ResponseEntity.ok("Message received successfully! Thank you.");

        } catch (Exception e) {
            // LOG THE CRASH AND RETURN 500
            System.err.println("--- EMAIL SEND FAILED ---");
            e.printStackTrace();
            System.err.println("-------------------------");

            return ResponseEntity.internalServerError().body("Message saved, but email notification failed: " + e.getMessage());
        }
    }
}