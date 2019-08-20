package com.company.library.controller;

import com.company.library.DTO.Registration;
import com.company.library.model.User;
import com.company.library.model.VerificationToken;
import com.company.library.repository.VerificationTokenRepository;
import com.company.library.service.EmailService;
import com.company.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
public class ActivateAccountController {

    private final UserService userService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final EmailService emailService;

    @Autowired
    public ActivateAccountController(UserService userService, VerificationTokenRepository verificationTokenRepository, EmailService emailService) {
        this.userService = userService;
        this.verificationTokenRepository = verificationTokenRepository;
        this.emailService = emailService;
    }

    @PostMapping("/register")
    @Transactional
    public void registerUserAccount(@RequestBody Registration userDto, HttpServletRequest request) {
        User registered = userService.registerNewUserAccount(userDto);
        VerificationToken token = new VerificationToken(registered);
        VerificationToken savedToken = verificationTokenRepository.save(token);

        URL url = null;
        try {
            url = new URL(request.getRequestURL().toString().replace("/register", "") + "/registerConfirm?token=" + savedToken.getToken());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(registered.getEmail());
        message.setSubject("Welcome to our library platform!");
        message.setText(String.format("Please click the following link to confirm your account activation : %s", url));
        emailService.sendEmail(message);
    }

    @GetMapping("/registerConfirm")
    public String registerConfirm(@RequestParam("token") String token) {
        VerificationToken storedToken = verificationTokenRepository.findByToken(token);

        if (storedToken == null) {
            return "The token you have provided is invalid!";
        }

        if (storedToken.isExpired()) {
            verificationTokenRepository.delete(storedToken);
            userService.delete(storedToken.getUser());
            return "Your registration token has expired! Please try to register again!";
        }

        User registeredUser = storedToken.getUser();
        registeredUser.setEnabled(true);
        userService.save(registeredUser);

        return "Your account was successfully activated!";
    }

}
