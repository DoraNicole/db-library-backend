package com.company.library;

import com.company.library.model.*;
import com.company.library.repository.BookRepositoryInterface;
import com.company.library.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @Mock
    BookRepositoryInterface bookRepositoryInterface;

    @Mock
    BookService bookService2;

    @InjectMocks
    BookService bookService;

    @Test
    public void findBookByIsbnTest() {
        ImageModel imageModel = new ImageModel("img1", "img1", new byte[2]);
        User user = new User();
        user.setFirstName("Ana");
        user.setLastName("Dumitrescu");
        user.setEmail("mariaadumitrescu05@yahoo.com");
        user.setAdmin(false);
        Rating rating1 = new Rating(4.2, "aa", user, LocalDate.of(2019, 9, 9));
        List<Rating> ratings = new LinkedList<Rating>();
        ratings.add(rating1);
        Author author = new Author();
        author.setName("Lewis Carroll");
        List<Author> authors = new LinkedList<>();
        authors.add(author);
        Genre genre = new Genre();
        genre.setName("fantasy");
        List<Genre> genres = new LinkedList<>();
        genres.add(genre);

        Book book3 = new Book("1234567890113", "Alice in Wonderland3", authors, "RAO", 1865, genres, imageModel, ratings, "Nice book", 4);

        when(bookRepositoryInterface.findBookByIsbn("1234567890113")).thenReturn(book3);

        assertEquals(book3, bookService.findBookByIsbn("1234567890113"));
    }



    @Test
    public void setAverageStarsTest() {
        ImageModel imageModel = new ImageModel("img1", "img1", new byte[2]);
        User user = new User();
        user.setFirstName("Ana");
        user.setLastName("Dumitrescu");
        user.setEmail("mariaadumitrescu05@yahoo.com");
        user.setAdmin(false);
        Rating rating1 = new Rating(4.0, "aa", user, LocalDate.of(2019, 9, 9));
        Rating rating2 = new Rating(5.0, "aa", user, LocalDate.of(2019, 8, 9));
        Rating rating3 = new Rating(3.0, "aa", user, LocalDate.of(2019, 9, 12));
        Rating rating4 = new Rating(5.0, "aa", user, LocalDate.of(2019, 6, 18));
        List<Rating> ratings = new LinkedList<Rating>();
        ratings.add(rating1);
        ratings.add(rating2);
        ratings.add(rating3);
        ratings.add(rating4);

        Author author = new Author();
        author.setName("Lewis Carroll");
        List<Author> authors = new LinkedList<>();
        authors.add(author);
        Genre genre = new Genre();
        genre.setName("fantasy");
        List<Genre> genres = new LinkedList<>();
        genres.add(genre);

        Book book3 = new Book("1234567890113", "Alice in Wonderland3", authors, "RAO", 1865, genres, imageModel, ratings, "Nice book", 4);

        when(bookService2.findBookByIsbn("1234567890113")).thenReturn(book3);
        assertEquals(4.25, bookService.setAverageStars(book3), 0);
    }






    /*
    * ImageModel imageModel = new ImageModel("img1", "img1", new byte[2]);
        User user = new User();
        user.setFirstName("Ana");
        user.setLastName("Dumitrescu");
        user.setEmail("mariaadumitrescu05@yahoo.com");
        user.setAdmin(false);
        Rating rating1 = new Rating(4.0, "aa", user, LocalDate.of(2019, 9, 9));
        Rating rating2 = new Rating(5.0, "aa", user, LocalDate.of(2019, 8, 9));
        Rating rating3 = new Rating(3.0, "aa", user, LocalDate.of(2019, 9, 12));
        Rating rating4 = new Rating(5.0, "aa", user, LocalDate.of(2019, 6, 18));
        List<Rating> ratings = new LinkedList<Rating>();
        ratings.add(rating1);
        ratings.add(rating2);
        ratings.add(rating3);
        ratings.add(rating4);

        Author author = new Author();
        author.setName("Lewis Carroll");
        List<Author> authors = new LinkedList<>();
        authors.add(author);
        Genre genre = new Genre();
        genre.setName("fantasy");
        List<Genre> genres = new LinkedList<>();
        genres.add(genre);

        //Book book = new Book("1234567890111", "Alice in Wonderland", authors, "RAO", 1865, genres, imageModel, ratings, "Nice book", 4);
       // Book book2 = new Book("1234567890112", "Alice in Wonderland2", authors, "RAO", 1865, genres, imageModel, ratings, "Nice book", 4);
        Book book3 = new Book("1234567890113", "Alice in Wonderland3", authors, "RAO", 1865, genres, imageModel, ratings, "Nice book", 4);
       // Book book4 = new Book("1234567890114", "Alice in Wonderland4", authors, "RAO", 1865, genres, imageModel, ratings, "Nice book", 4);


       // List<Book> books = new LinkedList<>();

       // books.add(book);
      //  books.add(book2);
      //  books.add(book3);
     //   books.add(book4);

*
    *
    * */


}
