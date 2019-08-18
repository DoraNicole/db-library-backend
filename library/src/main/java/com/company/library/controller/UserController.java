package com.company.library.controller;

import com.company.library.model.Role;
import com.company.library.model.User;
import com.company.library.repository.RoleRepository;
import com.company.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/allusers")
    List<User> findAll() {
        return userService.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/changeRole/{id}")
    public void changeRole(@PathVariable(value = "id") Long userId) {
        Optional<User> userOptional = userService.findById(userId);

        if (userOptional.isPresent()) {

            User user = userOptional.get();
            Role userRole = roleRepository.findByName("ROLE_ADMIN");
            user.setAdmin(true);

            user.setRoles(new HashSet<>(Collections.singleton(userRole)));

            userService.save(user);

        } else {
            throw new IllegalArgumentException("Nu exista user-ul cu id-ul " + userId);
        }
    }

}
