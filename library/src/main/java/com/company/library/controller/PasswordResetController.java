package com.company.library.controller;

import com.company.library.DTO.PasswordResetDTO;
import com.company.library.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PasswordResetController {


    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;


    @PostMapping("/forgotpassword")
    public ResponseEntity<?> forgotPassword(@RequestBody PasswordResetDTO passwordResetDTO) {
        return ResponseEntity.ok(jwtUserDetailsService.saveNewPassword(passwordResetDTO.getEmail(), passwordResetDTO.getPassword()));
    }

}
