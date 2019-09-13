package com.company.library.controller;

import com.company.library.DTO.Registration;
import com.company.library.exceptions.EmailExistsException;
import com.company.library.exceptions.VerificationTokenException;
import com.company.library.model.User;
import com.company.library.model.VerificationToken;
import com.company.library.repository.VerificationTokenRepository;
import com.company.library.service.EmailService;
import com.company.library.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
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

//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(registered.getEmail());
//        message.setSubject("Welcome to our library platform!");
//        message.setText(String.format("Please click the following link to confirm your account activation : %s", url));

//        emailService.sendEmail(message);
        emailService.sendActivateAccountEmail(userService.findUserByEmail(userDto.getEmail()), url.toString());

          return registered;

    }

    @PostMapping("/resendVerificationLink")
    public void resendVerification(HttpServletRequest request, @RequestBody String email) {

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

        oldToken.setExpiryDateTime(LocalDateTime.from(LocalDateTime.of(2000, 11, 12, 0, 0, 0)));
        VerificationToken newToken = new VerificationToken(user);
        VerificationToken savedToken = verificationTokenRepository.save(newToken);

        URL url = null;
        try {
            url = new URL(request.getRequestURL().toString().replace("/resendVerificationLink", "") + "/registerConfirm?token=" + savedToken.getToken());
        } catch (MalformedURLException e) {
            log.error("The user is currently disabled!");
            e.printStackTrace();
        }

//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(user.getEmail());
//        message.setSubject("Welcome to our library platform!");
//        message.setText(String.format("Your code is: " + savedToken.getToken() + " Please click the following link to confirm your account activation : %s", url));
//        emailService.sendEmail(message);
        emailService.sendWelcomeEmail(user);
    }

    @GetMapping("/registerConfirm")
    public void registerConfirm(@RequestParam("token") String token) {
        VerificationToken storedToken = verificationTokenRepository.findByToken(token);

        if (storedToken == null) {
            throw new VerificationTokenException("Invalid token provided!");
        }

        if (storedToken.isExpired()) {
            verificationTokenRepository.delete(storedToken);
            userService.delete(storedToken.getUser());
            throw new VerificationTokenException("The token is expired! Please register again!");
        }

        User registeredUser = storedToken.getUser();
        registeredUser.setEnabled(true);
        userService.save(registeredUser);
        verificationTokenRepository.delete(storedToken);


    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/findVerificationTokenByEmail")
    public VerificationToken findVerificationTokenByEmail(@RequestParam("email") String email) {
        return this.verificationTokenRepository.findAll().stream().filter(verificationToken -> verificationToken.getUser().getEmail().equals(email)).findFirst().orElse(null);
    }
}