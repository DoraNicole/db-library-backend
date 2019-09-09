package com.company.library.service;

import com.company.library.enums.Direction;
import com.company.library.enums.OrderBy;
import com.company.library.exceptions.PaginationSortingException;
import com.company.library.exceptions.PagingSortingErrorResponse;
import com.company.library.model.*;
import com.company.library.repository.BookRepositoryInterface;
import com.company.library.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Service
public class RatingService implements RatingServiceInterface {


    @Autowired
    private RatingRepository ratingService;

    @Autowired
    BookRepositoryInterface bookRepositoryInterface;

    @Override
    public void addRating(Rating rating, Long id) {
        Book book = bookRepositoryInterface.findBookById(id);
        book.getRatings().add(rating);
        bookRepositoryInterface.saveAndFlush(book);
    }

    @Override
    public void deleteRating(Rating rating) {
        ratingService.delete(rating);
    }

    @Override
    public void deleteRatingById(Long id) {
        ratingService.deleteById(id);
    }

    @Override
    public ResponsePageList<Rating> findRatingByUserNameOrLastName(String orderBy, String direction, int page, int size, String query) {

        Sort sort = null;
        if (direction.equals("ASC")) {
            sort = new Sort(Sort.Direction.ASC, orderBy);
        }
        if (direction.equals("DESC")) {
            sort = new Sort(Sort.Direction.DESC, orderBy);
        }

        if (!(direction.equals(Direction.ASCENDING.getDirectionCode()) || direction.equals(Direction.DESCENDING.getDirectionCode()))) {
            throw new PaginationSortingException("Invalid sort direction");
        }
        if (!(orderBy.equals(OrderBy.ID.getOrderByCode()) || orderBy.equals(OrderBy.TITLE.getOrderByCode()))) {
            throw new PaginationSortingException("Invalid orderBy condition");
        }

        Predicate<Rating> containAuthorFirstName = rating ->  rating.getUser().getFirstName().equalsIgnoreCase(query);
        Predicate<Rating> containAuthorLastName = rating ->  rating.getUser().getLastName().equalsIgnoreCase(query);
        List<Rating> ratingList = ratingService.findAll(sort).stream().filter(containAuthorFirstName.or(containAuthorLastName)).collect(Collectors.toList());

        PagedListHolder<Rating> pagedListHolder = new PagedListHolder<>(ratingList);
        pagedListHolder.setPageSize(size);
        pagedListHolder.setPage(page);
        ResponsePageList<Rating> response = new ResponsePageList<>();
        response.setNrOfElements(pagedListHolder.getNrOfElements());
        response.setPageList(pagedListHolder.getPageList());
        return response;
    }
    @Override
    public ResponsePageList<Rating> findPaginatedRatings(String orderBy, String direction, int page, int size, String bookId) {
        Sort sort = null;
        if (direction.equals("ASC")) {
            sort = new Sort(Sort.Direction.ASC, orderBy);
        }
        if (direction.equals("DESC")) {
            sort = new Sort(Sort.Direction.DESC, orderBy);
        }

        if (!(direction.equals(Direction.ASCENDING.getDirectionCode()) || direction.equals(Direction.DESCENDING.getDirectionCode()))) {
            throw new PaginationSortingException("Invalid sort direction");
        }
        if (!(orderBy.equals(OrderBy.ID.getOrderByCode()) || orderBy.equals(OrderBy.TITLE.getOrderByCode()))) {
            throw new PaginationSortingException("Invalid orderBy condition");
        }

        Book book = bookRepositoryInterface.findById(Long.parseLong(bookId)).orElse(null);
        List<Rating> ratingList = book.getRatings().stream().collect(Collectors.toList());

        PagedListHolder<Rating> pagedListHolder = new PagedListHolder<>(ratingList);
        pagedListHolder.setPageSize(size);
        pagedListHolder.setPage(page);
        ResponsePageList<Rating> response = new ResponsePageList<>();
        response.setNrOfElements(pagedListHolder.getNrOfElements());
        response.setPageList(pagedListHolder.getPageList());
        return response;

    }

    @Override
    @ExceptionHandler(PaginationSortingException.class)
    public ResponseEntity<PagingSortingErrorResponse> exceptionHandler(Exception ex) {
        PagingSortingErrorResponse pagingSortingErrorResponse = new PagingSortingErrorResponse();
        pagingSortingErrorResponse.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
        pagingSortingErrorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(pagingSortingErrorResponse, HttpStatus.OK);
    }

}
