package com.company.library.controller;

import com.company.library.model.Book;
import com.company.library.model.Genre;
import com.company.library.service.UserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/preferences")
public class UserPreferenceController {

    private final UserPreferenceService userPreferenceService;

    @Autowired
    public UserPreferenceController(UserPreferenceService userPreferenceService) {
        this.userPreferenceService = userPreferenceService;
    }

    @PostMapping
    public void save(@RequestBody List<Genre> preferences, @AuthenticationPrincipal UserDetails userDetails) {
        userPreferenceService.save(preferences, userDetails);
    }

    @GetMapping
    public Collection<Genre> getAll(@AuthenticationPrincipal UserDetails userDetails) {
        return userPreferenceService.getAllFor(userDetails);
    }

    @GetMapping("/books")
    public Collection<Book> getPreferredBooks(@AuthenticationPrincipal UserDetails userDetails) {
        return userPreferenceService.getPreferredBooksFor(userDetails);
    }

}
