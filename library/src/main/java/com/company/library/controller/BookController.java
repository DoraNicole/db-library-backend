package com.company.library.controller;

import com.company.library.model.Book;
import com.company.library.service.BookServiceInterface;
import com.company.library.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class BookController {

    @Autowired
    private BookServiceInterface bookService;

    @Autowired
    private UserServiceInterface userService;

    @GetMapping("/books")
    @ResponseBody
    public List<Book> get() {
        return bookService.getBooks();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public void addBook(@RequestBody Book book) {
        bookService.addBook(book);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/remove/{id}")
    public void removeBook(@PathVariable(value = "id") Long id) {
        bookService.remove(id);
    }

    @GetMapping("/searchBook")
    public List<Book> searchBook(@RequestParam("query") String query) {
        return bookService.searchBook(query);
    }
}
