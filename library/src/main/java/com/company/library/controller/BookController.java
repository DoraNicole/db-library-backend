package com.company.library.controller;

import com.company.library.model.Book;
import com.company.library.service.BookServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @GetMapping("/paginatedBooks")
    public PagedListHolder findPaginatedBooks(
            @RequestParam("orderBy") String orderBy,
            @RequestParam("direction") String direction,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("query") String query
    ) {
        return bookService.findPaginatedBooks(orderBy, direction, page, size, query);
    }
}
