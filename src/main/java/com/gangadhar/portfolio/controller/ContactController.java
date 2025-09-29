package com.gangadhar.portfolio.controller;

import com.gangadhar.portfolio.model.Contact; // Required to resolve Contact
import com.gangadhar.portfolio.repository.ContactRepository; // Required to resolve ContactRepository
import com.gangadhar.portfolio.service.EmailService; // Required to resolve EmailService

import org.springframework.http.ResponseEntity; // Required to resolve ResponseEntity
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactRepository contactRepository;
    private final EmailService emailService; // Field for EmailService

    // 1. CONSTRUCTOR: Inject both the Repository and the Service
    public ContactController(ContactRepository contactRepository, EmailService emailService) {
        this.contactRepository = contactRepository;
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<String> submitContact(@RequestBody Contact contact) {

        // 2. Save the contact data first
        contactRepository.save(contact);

        try {
            // 3. Send email notifications
            emailService.sendNewContactMessage(contact); // Notification to admin (you)
            emailService.sendAutoReply(contact);        // Auto-reply to user

            // If both emails are sent successfully
            return ResponseEntity.ok("Message received successfully! Thank you.");

        } catch (Exception e) {
            // 4. LOG THE ERROR AND RETURN A FAILURE RESPONSE
            System.err.println("--- EMAIL SEND FAILED ---");
            e.printStackTrace();
            System.err.println("-------------------------");

            // Return a 500 Internal Server Error, but indicate data was saved
            return ResponseEntity.internalServerError().body("Message saved, but email notification failed: " + e.getMessage());
        }
    }
}