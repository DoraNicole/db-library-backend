package com.company.library.service;

import com.company.library.model.Book;
import com.company.library.model.ResponsePageList;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface BookServiceInterface {

    void addBook(Book b);
    List<Book> getBooks();
    void remove(Long bookId);
    ResponsePageList<Book> findPaginatedBooks(String orderBy, String direction, int page, int size, String query);
    Page<Book> findPreferredBooks(String orderBy, String direction, int page, int size, UserDetails userDetails);
    ResponsePageList<Book> findSameGenreBooks(String orderBy, String direction, int page, int size, String id);
    //Book findBookByTitleAndAuthor(String title, String author);
    Book findBookByIsbn(String isbn);
    Book findBookById(Long id);
}
