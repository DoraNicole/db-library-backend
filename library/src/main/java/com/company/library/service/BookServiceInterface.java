package com.company.library.service;

import com.company.library.model.Book;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookServiceInterface {
    void addBook(Book b);
    List<Book> getBooks();
    void remove(Long bookId);
    List<Book> searchBook(String query);
    Page<Book> findPaginatedBooks(String orderBy, String direction, int page, int size);
}
