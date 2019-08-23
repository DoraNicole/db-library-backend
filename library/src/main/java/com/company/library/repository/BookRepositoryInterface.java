package com.company.library.repository;

import com.company.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepositoryInterface extends JpaRepository<Book, Long>, PagingAndSortingRepository<Book, Long> {

    public Book findBookById(Long id);
    public Book findBookByIsbn(String isbn);
    public Book findBookByTitleAndAuthor(String title, String author);
}
