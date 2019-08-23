package com.company.library.service;

import com.company.library.enums.Direction;
import com.company.library.enums.OrderBy;
import com.company.library.exceptions.PaginationSortingException;
import com.company.library.exceptions.PagingSortingErrorResponse;
import com.company.library.model.Book;
import com.company.library.model.Rating;
import com.company.library.model.ResponsePageList;
import com.company.library.repository.BookRepositoryInterface;
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
public class BookService implements BookServiceInterface {

    @Autowired
    private BookRepositoryInterface bookRepositoryInterface;

    @Override
    public void addBook(Book book) {
        bookRepositoryInterface.save(book);
    }

    @Override
    public List<Book> getBooks() {
        return bookRepositoryInterface.findAll();
    }

    @Override
    public void remove(Long bookId) {
        bookRepositoryInterface.deleteById(bookId);
    }

    @Override
    public List<Book> searchBook(String query) {
        Predicate<Book> searchByTitle = b -> b.getTitle().toLowerCase().contains(query.toLowerCase());
        //Predicate<Book> searchByGenre = b -> b.getGenre().toLowerCase().contains(query.toLowerCase());
        Predicate<Book> searchByAuthor = b -> b.getAuthor().toLowerCase().contains(query.toLowerCase());
        return bookRepositoryInterface.findAll().stream().filter(searchByTitle.or(searchByAuthor)).limit(100L).collect(Collectors.toList());
    }

    @Override
    public ResponsePageList findPaginatedBooks(String orderBy, String direction, int page, int size, String query) {
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
        List<Book> list = bookRepositoryInterface.findAll(sort).stream().filter(book -> book.getTitle().toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList());

        PagedListHolder<Book> pagedListHolder = new PagedListHolder<>(list);
        pagedListHolder.setPageSize(size);
        pagedListHolder.setPage(page);
        ResponsePageList response = new ResponsePageList();
        response.setNrOfElements(pagedListHolder.getNrOfElements());
        response.setPageList(pagedListHolder.getPageList());
        return response;

    }

    @Override
    public Book findBookByTitleAndAuthor(String title, String author) {
        return bookRepositoryInterface.findBookByTitleAndAuthor(title, author);
    }

    @ExceptionHandler(PaginationSortingException.class)
    public ResponseEntity<PagingSortingErrorResponse> exceptionHandler(Exception ex) {
        PagingSortingErrorResponse pagingSortingErrorResponse = new PagingSortingErrorResponse();
        pagingSortingErrorResponse.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
        pagingSortingErrorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(pagingSortingErrorResponse, HttpStatus.OK);
    }

    @Override
    public Book findBookByIsbn(String isbn) {
        return bookRepositoryInterface.findBookByIsbn(isbn);
    }


    public double setAverageStars(Book book) {
        book = findBookByIsbn(book.getIsbn());
        double result = 0;
        List<Rating> ratings = book.getRatings();
        int number = ratings.size();
        for (Rating i : ratings) {
            result = i.getValue() + result;
        }

        return result / number;
    }

}
