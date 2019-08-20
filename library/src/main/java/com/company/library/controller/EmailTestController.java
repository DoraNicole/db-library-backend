package com.company.library.controller;

import com.company.library.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailTestController {

    private final EmailService emailService;

    @Autowired
    public EmailTestController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping
    public void testEmail() {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("amaliastefanescu0@gmail.com");
        msg.setSubject("Test subject");
        msg.setText("Test body");
        emailService.sendEmail(msg);
    }
}
