package com.company.library.controller;

import com.company.library.model.Book;
import com.company.library.model.ResponsePageList;
import com.company.library.service.BookServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class BookController {

    @Autowired
    private BookServiceInterface bookService;

    @GetMapping("/books")
    public List<Book> get() {
        return bookService.getBooks();
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addBook")
    public void addBook(@RequestBody Book book) {
        bookService.addBook(book);
    }

    //@PreAuthorize("hasRole('ADMIN')")
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
}
