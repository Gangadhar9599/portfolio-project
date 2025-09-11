package com.gangadhar.portfolio.controller;

import com.gangadhar.portfolio.model.Contact;
import com.gangadhar.portfolio.repository.ContactRepository;
//import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contact")
public class ContactController {
    private final ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    //@PostMapping
    //public ResponseEntity<String> submitContact() {
      //  ResponseEntity<String> stringResponseEntity = submitContact(null);
        //return stringResponseEntity;
    //}

    @PostMapping
    public ResponseEntity<String> submitContact( @RequestBody Contact contact) {
        contactRepository.save(contact);
        return ResponseEntity.ok("Message received successfully!");
    }
}