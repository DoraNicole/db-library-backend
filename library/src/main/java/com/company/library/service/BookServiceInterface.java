package com.company.library.service;

import com.company.library.model.Book;
import org.springframework.beans.support.PagedListHolder;


import java.util.List;

public interface BookServiceInterface {
    void addBook(Book b);
    List<Book> getBooks();
    void remove(Long bookId);
    List<Book> searchBook(String query);
    PagedListHolder findPaginatedBooks(String orderBy, String direction, int page, int size, String query);
}
