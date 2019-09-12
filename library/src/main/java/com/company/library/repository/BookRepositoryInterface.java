package com.company.library.repository;

import com.company.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BookRepositoryInterface extends JpaRepository<Book, Long>, PagingAndSortingRepository<Book, Long> {

    Book findBookById(Long id);
    Book findBookByIsbn(String isbn);
    Collection<Book> findBookByGenresNameIn(List<String> genreList);
}
