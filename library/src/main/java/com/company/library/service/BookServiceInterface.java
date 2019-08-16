package com.company.library.service;

import com.company.library.model.Book;

import java.util.List;

public interface BookServiceInterface {
    void addBook(Book b);
    List<Book> getBooks();
    void remove(Long bookId);
    List<Book> searchBook(String query);
}
