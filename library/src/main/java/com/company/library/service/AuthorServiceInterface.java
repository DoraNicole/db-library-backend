package com.company.library.service;

import com.company.library.exceptions.PaginationSortingException;
import com.company.library.exceptions.PagingSortingErrorResponse;
import com.company.library.model.Author;
import com.company.library.model.ResponsePageList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

public interface AuthorServiceInterface {

    List<Author> getAuthorsList();

    Author getAuthorByName(String query);

    List<Author> addAuthors(List<Author> author);

    boolean checkIfAuthorExist(String query);

    void deleteAuthor(Author author);

    Author getAuthorById(Long id);

    void deleteAuthorById(Long id);

    ResponsePageList<Author> findAuthorByName(String orderBy, String direction, int page, int size, String query);

    @ExceptionHandler(PaginationSortingException.class)
    ResponseEntity<PagingSortingErrorResponse> exceptionHandler(Exception ex);
}
