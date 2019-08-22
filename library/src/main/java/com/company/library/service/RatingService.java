package com.company.library.service;

import com.company.library.model.Book;
import com.company.library.model.Rating;
import com.company.library.repository.BookRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RatingService implements RatingServiceInterface{


    @Autowired
    BookRepositoryInterface bookRepositoryInterface;

    public void addRating(Book book, Rating rating, List<Rating> ratings) {
        book = bookRepositoryInterface.findBookById(book.getId());
        ratings.add(rating);
        book.setRatings(ratings);
    }

}
