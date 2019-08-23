package com.company.library.service;

import com.company.library.DTO.BookRatingDTO;
import com.company.library.model.Book;
import com.company.library.model.Rating;
import com.company.library.repository.BookRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RatingService implements RatingServiceInterface {


    @Autowired
    BookRepositoryInterface bookRepositoryInterface;

    @Override
    public void addRating(BookRatingDTO bookRatingDTO) {
        Book book = bookRepositoryInterface.findBookByIsbn(bookRatingDTO.getIsbn());
        Rating rating = new Rating();
        rating.setDescription(bookRatingDTO.getDescription());
        rating.setValue(bookRatingDTO.getValue());

        List<Rating> ratingList = book.getRatings();
        ratingList.add(rating);

        book.setRatings(ratingList);

        bookRepositoryInterface.saveAndFlush(book);
    }




}
