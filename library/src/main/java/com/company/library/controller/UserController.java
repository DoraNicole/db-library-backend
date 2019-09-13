package com.company.library.controller;

import com.company.library.model.ResponsePageList;
import com.company.library.model.Role;
import com.company.library.model.User;
import com.company.library.model.UserBook;
import com.company.library.repository.RoleRepository;
import com.company.library.service.EmailService;
import com.company.library.service.UserBookServiceInterface;
import com.company.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private EmailService emailService;

    @GetMapping("/allusers")
    List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/findUserByEmail")
    ResponseEntity<User> findUserByEmail(@RequestParam("email") String email) {
        return new ResponseEntity<>(userService.findUserByEmail(email), HttpStatus.OK);
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
            throw new IllegalArgumentException("The user with this id: " + userId + " does not exist!");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/paginatedUsers")
    public ResponseEntity<ResponsePageList<User>> findPaginatedUsers(
            @RequestParam("orderBy") String orderBy,
            @RequestParam("direction") String direction,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("query") String query
    ) {
        return new ResponseEntity<>(userService.findPaginatedUsers(orderBy, direction, page, size, query), HttpStatus.OK);
    }

    @PostMapping("/updateUser")
    public void updateUser(@RequestBody User user){
       userService.save(user);
    }

    @PostMapping("/clearPenalties")
    public void clearPenalties(@RequestBody User user){
        userService.clearPenalties(user);
    }

    @PostMapping("/checkForPenalties")
   public void checkForPenalties(@RequestBody User user){
        userService.checkForPenalties(user);
    }


}
