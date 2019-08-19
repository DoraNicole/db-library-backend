package com.company.library.service;

import com.company.library.model.Book;
import com.company.library.repository.BookRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
//        Predicate<Book> searchByGenre = b -> b.getGenre().stream().filter(g -> g.getName() == query.toLowerCase());
//        Predicate<Book> searchByAuthor = b -> b.getAuthor().toLowerCase().contains(query.toLowerCase());
        return bookRepositoryInterface.findAll().stream().filter(searchByTitle).collect(Collectors.toList());
    }
}
