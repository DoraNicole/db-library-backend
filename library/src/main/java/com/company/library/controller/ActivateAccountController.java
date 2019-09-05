package com.company.library.controller;

import com.company.library.DTO.Registration;
import com.company.library.exceptions.EmailExistsException;
import com.company.library.model.User;
import com.company.library.model.VerificationToken;
import com.company.library.repository.VerificationTokenRepository;
import com.company.library.service.EmailService;
import com.company.library.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

@RestController
public class ActivateAccountController {


    private final UserService userService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final EmailService emailService;

    private Logger log = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    public ActivateAccountController(UserService userService, VerificationTokenRepository verificationTokenRepository, EmailService emailService) {
        this.userService = userService;
        this.verificationTokenRepository = verificationTokenRepository;
        this.emailService = emailService;
    }

    @PostMapping("/register")
    @Transactional
    public User registerUserAccount(@RequestBody Registration userDto, HttpServletRequest request) throws EmailExistsException {

        User registered = userService.registerNewUserAccount(userDto);
        VerificationToken token = new VerificationToken(registered);
        VerificationToken savedToken = verificationTokenRepository.save(token);

        URL url = null;
        try {
            url = new URL(request.getRequestURL().toString().replace("/register", "") + "/registerConfirm?token=" + savedToken.getToken());
        } catch (MalformedURLException e) {
            log.error("The user is currently disabled!");
            e.printStackTrace();
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(registered.getEmail());
        message.setSubject("Welcome to our library platform!");
        message.setText(String.format("Please click the following link to confirm your account activation : %s", url));
        emailService.sendEmail(message);
        return registered;

    }

    @PostMapping("/resendVerificationLink")
    public String resendVerification(HttpServletRequest request, @RequestBody String email) {

        User user = userService.findUserByEmail(email);

        if (user == null) {
            log.error("The email {} does not correspond to an active user", email);
            throw new RuntimeException("The email " + email + " does not correspond to an active user");
        }

        VerificationToken oldToken = verificationTokenRepository.findActiveByUserEmail(email);

        if (oldToken == null) {
            log.error("The email {} does not have an active associated token!", email);
            throw new RuntimeException("The user " + email + " does not have an active associated token!");
        }

        oldToken.setExpiryDateTime(LocalDateTime.from(LocalDateTime.of(2000,11,12, 0, 0, 0)));
        VerificationToken newToken = new VerificationToken(user);
        VerificationToken savedToken = verificationTokenRepository.save(newToken);

        URL url = null;
        try {
            url = new URL(request.getRequestURL().toString().replace("/resendVerificationLink", "") + "/registerConfirm?token=" + savedToken.getToken());
        } catch (MalformedURLException e) {
            log.error("The user is currently disabled!");
            e.printStackTrace();
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Welcome to our library platform!");
        message.setText(String.format("Please click the following link to confirm your account activation : %s", url));
        emailService.sendEmail(message);
        return "successfully resent verification link";
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