package com.company.library.controller;

import com.company.library.model.Book;
import com.company.library.model.Rating;
import com.company.library.model.ResponsePageList;
import com.company.library.service.BookServiceInterface;
import com.company.library.service.RatingServiceInterface;
import com.company.library.service.UserBookServiceInterface;
import com.company.library.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class BookController {

    @Autowired
    private BookServiceInterface bookService;

    @Autowired
    private RatingServiceInterface ratingService;


    @Autowired
    private UserServiceInterface userService;

    @Autowired
    private UserBookServiceInterface userBookService;

    @GetMapping("/books")
    public List<Book> get() {
        return bookService.getBooks();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addBook")
    public void addBook(@RequestBody Book book) {
        bookService.addBook(book);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/remove/{id}")
    public void removeBook(@PathVariable(value = "id") Long id) {
        bookService.remove(id);
    }

    @GetMapping("/paginatedBooks")
    public ResponseEntity<ResponsePageList<Book>> findPaginatedBooks(
            @RequestParam("orderBy") String orderBy,
            @RequestParam("direction") String direction,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("query") String query
    ) {
        return new ResponseEntity<>(bookService.findPaginatedBooks(orderBy, direction, page, size, query), HttpStatus.OK);
    }

    @GetMapping("/preferredBooks")
    public ResponseEntity<ResponsePageList<Book>> findPreferredBooks(
            @RequestParam("orderBy") String orderBy,
            @RequestParam("direction") String direction,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("id") String id
    ) {
        return new ResponseEntity<>(bookService.findPreferredBooks(orderBy, direction, page, size, id), HttpStatus.OK);
    }

    @GetMapping("/searchBookById")
    public Book searchBookById(@RequestParam("id") Long id){
        return bookService.findBookById(id);
    }
}
