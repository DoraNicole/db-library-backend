package com.company.library.controller;

import com.company.library.model.Book;
import com.company.library.model.Rating;
import com.company.library.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RatingsController {

    @Autowired
    RatingService ratingService;


    @PostMapping("/book/addrating")
    public void addRating(@RequestBody Book book, @RequestBody Rating rating) {
        ratingService.addRating(book, rating, book.getRatings());
    }



}
