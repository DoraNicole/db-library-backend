package com.company.library.controller;


import com.company.library.model.Rating;
import com.company.library.model.ResponsePageList;
import com.company.library.service.RatingServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/paginatedRatings")
    public ResponseEntity<ResponsePageList<Rating>> findPaginatedComments(
            @RequestParam("orderBy") String orderBy,
            @RequestParam("direction") String direction,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("bookId") String bookId
    ) {
        return new ResponseEntity<>(ratingService.findPaginatedRatings(orderBy, direction, page, size, bookId), HttpStatus.OK);
    }
}
