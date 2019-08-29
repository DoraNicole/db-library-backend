package com.company.library.controller;


import com.company.library.model.Rating;
import com.company.library.service.RatingServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class RatingController {

    @Autowired
    RatingServiceInterface ratingService;

    @PostMapping("/addRating")
    public void addRatings(@RequestBody Rating rating,
                           @RequestParam("id") Long id) {
        ratingService.addRating(rating, id);
    }
}
