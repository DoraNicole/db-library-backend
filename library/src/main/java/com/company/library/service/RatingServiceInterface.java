package com.company.library.service;

import com.company.library.exceptions.PaginationSortingException;
import com.company.library.exceptions.PagingSortingErrorResponse;
import com.company.library.model.Book;
import com.company.library.model.Rating;
import com.company.library.model.ResponsePageList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

public interface RatingServiceInterface {
    void addRating(Rating rating, Long id);
    void deleteRating(Rating rating);
    void deleteRatingById(Long id);
    ResponsePageList<Rating> findRatingByUserNameOrLastName(String orderBy, String direction, int page, int size, String query);
    @ExceptionHandler(PaginationSortingException.class)
    ResponseEntity<PagingSortingErrorResponse> exceptionHandler(Exception ex);
    ResponsePageList<Rating> findPaginatedRatings(String orderBy, String direction, int page, int size, String bookId);
}
