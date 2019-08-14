package com.company.library.controller;

import com.company.library.DTO.Registration;
import com.company.library.exceptions.EmailExistsException;
import com.company.library.model.User;
import com.company.library.registration.OnRegistrationCompleteEvent;
import com.company.library.service.UserServiceInterface;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class RegistrationController {

//    @GetMapping(value = "/login/registration")
//    public String registrationForm(WebRequest request, Model model) {
//        Registration userObject = new Registration();
//        model.addAttribute("login", userObject);
//        return "registration";
//    }

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    UserServiceInterface userService;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @PostMapping(value = "/registration")
    public void registerUserAccount(@RequestBody @Valid final Registration account, final HttpServletRequest request) throws EmailExistsException {
        LOGGER.debug("Registering user account with information: {}", account);

        final User registered = userService.registerNewUserAccount(account);
        applicationEventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), request.getContextPath()));

    }



}
