package com.company.library.service;

import com.company.library.enums.Direction;
import com.company.library.enums.OrderBy;
import com.company.library.exceptions.PaginationSortingException;
import com.company.library.exceptions.PagingSortingErrorResponse;
import com.company.library.model.Genre;
import com.company.library.model.ResponsePageList;
import com.company.library.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class GenreService implements GenreServiceInterface {

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public List<Genre> addGenres(List<Genre> genres) {
        return genreRepository.saveAll(genres);
    }

    @Override
    public List<Genre> getGenresList() {
        return genreRepository.findAll();
    }

    @Override
    public Genre getGenreByName(String query) {
        return genreRepository.findAll().stream().filter(genre -> genre.getName().toLowerCase().contains(query.toLowerCase())).findFirst().orElse(null);
    }

    @Override
    public boolean checkIfGenreExist(String query) {
        return genreRepository.findAll().stream().anyMatch(genre -> genre.getName().toLowerCase().contains(query.toLowerCase()));
    }

    @Override
    public void deleteGenre(Genre genre) {
        genreRepository.delete(genre);
    }

    @Override
    public Genre getGenreById(Long id) {
        return genreRepository.getOne(id);
    }

    @Override
    public void deleteGenreById(Long id) {
        genreRepository.deleteById(id);
    }

    @Override
    public ResponsePageList<Genre> findGenreByName(String orderBy, String direction, int page, int size, String query) {

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

        Predicate<Genre> containName = genre -> genre.getName().equalsIgnoreCase(query);
        List<Genre> authorList = genreRepository.findAll(sort).stream().filter(containName).collect(Collectors.toList());

        PagedListHolder<Genre> pagedListHolder = new PagedListHolder<>(authorList);
        pagedListHolder.setPageSize(size);
        pagedListHolder.setPage(page);
        ResponsePageList<Genre> response = new ResponsePageList<>();
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
