package com.company.library.service;

import com.company.library.model.Book;
import com.company.library.repository.BookRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class BookService implements BookServiceInterface {

    @Autowired
    private BookRepositoryInterface bookRepositoryInterface;

    @Override
    public void addBook(Book book) {
        bookRepositoryInterface.save(book);
    }

    @Override
    public List<Book> getBooks() {
        return bookRepositoryInterface.findAll();
    }

    @Override
    public void remove(Long bookId) {
        bookRepositoryInterface.deleteById(bookId);
    }

    @Override
    public List<Book> searchBook(String query) {
        Predicate<Book> searchByTitle = b -> b.getTitle().toLowerCase().contains(query.toLowerCase());
        Predicate<Book> searchByGenre = b -> b.getGenre().toLowerCase().contains(query.toLowerCase());
        Predicate<Book> searchByAuthor = b -> b.getAuthor().toLowerCase().contains(query.toLowerCase());
        return bookRepositoryInterface.findAll().stream().filter(searchByTitle.or(searchByGenre).or(searchByAuthor)).limit(100L).collect(Collectors.toList());
    }

    @Override
    public Page<Book> findPaginatedBooks(String orderBy, String direction, int page, int size) {
        Sort sort = null;
        if (direction.equals("ASC")) {
            sort = new Sort(Sort.Direction.ASC, orderBy);
        }
        if (direction.equals("DESC")) {
            sort = new Sort(Sort.Direction.DESC, orderBy);
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Book> data = bookRepositoryInterface.findAll(pageable);

        return data;
    }
}
