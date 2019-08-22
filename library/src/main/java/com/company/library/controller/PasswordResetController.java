package com.company.library.controller;

import com.company.library.DTO.PasswordForgottenDTO;
import com.company.library.DTO.PasswordResetDTO;
import com.company.library.repository.UserRepositoryInterface;
import com.company.library.service.EmailService;
import com.company.library.service.PasswordResetService;
import com.company.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
public class PasswordResetController {

    private boolean link = false;
    private static final String linkFrontend = "http://localhost:4200";

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryInterface userRepositoryInterface;

    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/resetpassword")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetDTO passwordResetDTO, @RequestParam("random") String random) {

        link = true;
        return ResponseEntity.ok(userService.saveNewPassword(passwordResetDTO.getEmail(), passwordResetDTO.getPassword()));
    }

    @GetMapping("/forgotpassword")
    public void forgotPassword(@RequestBody PasswordForgottenDTO passwordForgottenDTO, HttpServletRequest request) {
        link = false;

        if (link == false) {
            if (userRepositoryInterface.findByEmail(passwordForgottenDTO.getEmail()) != null) {

                String random = passwordResetService.getAlphaNumericString(10);


                URL url = null;
                try {
                    //url = new URL(request.getRequestURL().toString().replace("/forgotpassword", "") + "/resetpassword?random=" + random);
                    url = new URL(linkFrontend+ "/resetpassword?random=" + random);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(passwordForgottenDTO.getEmail());
                message.setSubject("Password reset");
                message.setText(String.format("You have requested a password reset, here is your unique code: " + random + ". Now please click the following link to reset your password : %s", url));
                emailService.sendEmail(message);

            } else System.out.println("This email doesn't exist in the database!");

        }
        else System.out.println("Your link has expired!");
    }

}
