package com.company.library.service;

import com.company.library.model.Book;
import com.company.library.model.ResponsePageList;
import org.springframework.beans.support.PagedListHolder;


import java.util.List;
import java.util.Map;

public interface BookServiceInterface {

    void addBook(Book b);
    List<Book> getBooks();
    void remove(Long bookId);
    List<Book> searchBook(String query);
    ResponsePageList findPaginatedBooks(String orderBy, String direction, int page, int size, String query);
    Book findBookByTitleAndAuthor(String title, String author);
    Book findBookByIsbn(String isbn);
}
