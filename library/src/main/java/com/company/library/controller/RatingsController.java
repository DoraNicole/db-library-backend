package com.company.library.controller;

import com.company.library.DTO.BookDTO;
import com.company.library.DTO.BookRatingDTO;
import com.company.library.model.Book;
import com.company.library.model.Rating;
import com.company.library.service.BookService;
import com.company.library.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RatingsController {

    @Autowired
    RatingService ratingService;

    @Autowired
    BookService bookService;


    @PostMapping("/book/addrating")
    public void addRating(@RequestBody BookRatingDTO bookRatingDTO) {
        ratingService.addRating(bookRatingDTO);
    }

    @GetMapping("/bookrating")
    public List<Rating> bookRating(@RequestBody BookDTO bookDTO) {
        Book book = bookService.findBookByTitleAndAuthor(bookDTO.getTitle(), bookDTO.getAuthor());
        return book.getRatings();
    }



}
