package com.company.library.service;

import com.company.library.exceptions.PaginationSortingException;
import com.company.library.exceptions.PagingSortingErrorResponse;
import com.company.library.model.Genre;
import com.company.library.model.ResponsePageList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

public interface GenreServiceInterface {
    List<Genre> addGenres(List<Genre> genres);

    List<Genre> getGenresList();

    Genre getGenreByName(String query);

    boolean checkIfGenreExist(String query);

    void deleteGenre(Genre genre);

    Genre getGenreById(Long id);

    void deleteGenreById(Long id);

    ResponsePageList<Genre> findGenreByName(String orderBy, String direction, int page, int size, String query);

    @ExceptionHandler(PaginationSortingException.class)
    ResponseEntity<PagingSortingErrorResponse> exceptionHandler(Exception ex);
}
