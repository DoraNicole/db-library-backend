package com.company.library.service;

import com.company.library.model.Book;

import java.util.List;

public interface BookServiceInterface {
    public void addBook(Book b);

    public List<Book> getBooks();
    public void remove(Long bookId);
    List<Book> searchBook(String query);
}
