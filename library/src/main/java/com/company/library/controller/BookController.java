package com.company.library.controller;

import com.company.library.enums.Direction;
import com.company.library.enums.OrderBy;
import com.company.library.exceptions.PaginationSortingException;
import com.company.library.exceptions.PagingSortingErrorResponse;
import com.company.library.model.Book;
import com.company.library.service.BookServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class BookController {

    @Autowired
    BookServiceInterface bookService;

    @GetMapping("/books")
    public List<Book> get() {
        return bookService.getBooks();
    }

    @PostMapping("/add")
    public void addBook(@RequestBody Book book) {
        bookService.addBook(book);
    }

    @DeleteMapping("/remove/{id}")
    public void removeBook(@PathVariable(value = "id") Long id) {
        bookService.remove(id);
    }

    @GetMapping("/searchBook")
    public List<Book> searchBook(@RequestParam("query") String query) {
        return bookService.searchBook(query);
    }

    @RequestMapping(value = "/paginatedBooks", params = {"orderBy", "direction", "page", "size", "query"}, method = RequestMethod.GET)
    public PageImpl<Book> findPaginatedBooks(
            @RequestParam("orderBy") String orderBy,
            @RequestParam("direction") String direction,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("query") String query
            ) {
        if (!(direction.equals(Direction.ASCENDING.getDirectionCode()) || direction.equals(Direction.DESCENDING.getDirectionCode()))) {
            throw new PaginationSortingException("Invalid sort direction");
        }
        if (!(orderBy.equals(OrderBy.ID.getOrderByCode()) || orderBy.equals(OrderBy.TITLE.getOrderByCode()))) {
            throw new PaginationSortingException("Invalid orderBy condition");
        }
        List<Book> list = bookService.findPaginatedBooks(orderBy, direction, page, size).stream().filter(book -> book.getTitle().contains(query)).collect(Collectors.toCollection(ArrayList::new));
        return new PageImpl<>(list);
    }

    @ExceptionHandler(PaginationSortingException.class)
    public ResponseEntity<PagingSortingErrorResponse> exceptionHandler(Exception ex) {
        PagingSortingErrorResponse pagingSortingErrorResponse = new PagingSortingErrorResponse();
        pagingSortingErrorResponse.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
        pagingSortingErrorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(pagingSortingErrorResponse, HttpStatus.OK);
    }

}
