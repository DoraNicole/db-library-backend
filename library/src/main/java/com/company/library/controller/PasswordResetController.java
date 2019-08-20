package com.company.library.controller;

import com.company.library.DTO.PasswordForgottenDTO;
import com.company.library.DTO.PasswordResetDTO;
import com.company.library.DTO.Registration;
import com.company.library.service.EmailService;
import com.company.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
public class PasswordResetController {


    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/resetpassword")
    public ResponseEntity<?> forgotPassword(@RequestBody PasswordResetDTO passwordResetDTO) {
        return ResponseEntity.ok(userService.saveNewPassword(passwordResetDTO.getEmail(), passwordResetDTO.getPassword()));
    }

    @GetMapping("/forgotpassword")
    public void myController(@RequestBody PasswordForgottenDTO passwordForgottenDTO, HttpServletRequest request) {

        URL url = null;
        try {
            url = new URL(request.getRequestURL().toString().replace("/forgotpassword", "") + "/resetpassword" );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(passwordForgottenDTO.getEmail());
        message.setSubject("Password reset");
        message.setText(String.format("Please click the following link to reset your password : %s", url));
        emailService.sendEmail(message);

    }

}
