package com.company.library.service;

import com.company.library.enums.Direction;
import com.company.library.enums.OrderBy;
import com.company.library.exceptions.PaginationSortingException;
import com.company.library.exceptions.PagingSortingErrorResponse;
import com.company.library.model.*;
import com.company.library.repository.BookRepositoryInterface;
import com.company.library.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class BookService implements BookServiceInterface {

    @Autowired
    private BookRepositoryInterface bookRepositoryInterface;

    @Autowired
    private UserService userService;

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public void addBook(Book book) {
        //save genres
        List<Genre> databaseGenres = book.getGenres().stream()
                .map(g -> {
                    Genre dbGenre = genreRepository.findByName(g.getName());
                    if (dbGenre == null) {
                        return g;
                    } else {
                        return dbGenre;
                    }
                })
                .collect(Collectors.toList());

        book.setGenres(databaseGenres);

        //save book
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
    public ResponsePageList<Book> findPaginatedBooks(String orderBy, String direction, int page, int size, String query) {
        Sort sort = null;
        if (direction.equals("ASC")) {
            sort = new Sort(Sort.Direction.ASC, orderBy);
        }
        if (direction.equals("DESC")) {
            sort = new Sort(Sort.Direction.DESC, orderBy);
        }

        if (!(direction.equals(Direction.ASCENDING.getDirectionCode()) || direction.equals(Direction.DESCENDING.getDirectionCode()))) {
            throw new PaginationSortingException("Invalid sort direction");
        }
        if (!(orderBy.equals(OrderBy.ID.getOrderByCode()) || orderBy.equals(OrderBy.TITLE.getOrderByCode()))) {
            throw new PaginationSortingException("Invalid orderBy condition");
        }

        Predicate<Book> titleExist = book -> book.getTitle().toLowerCase().contains(query.toLowerCase());
        Predicate<Genre> foundInGenre = genre -> genre.getName().toLowerCase().contains(query.toLowerCase());
        Predicate<Book> genreExist = book -> book.getGenres().stream().anyMatch(foundInGenre);
        Predicate<Author> foundInAuthorName = author -> author.getName().toLowerCase().contains(query.toLowerCase());
        Predicate<Book> authorExist = book -> book.getAuthors().stream().anyMatch(foundInAuthorName);
        List<Book> list = bookRepositoryInterface.findAll(sort).stream().filter(titleExist.or(genreExist).or(authorExist)).collect(Collectors.toList());

        PagedListHolder<Book> pagedListHolder = new PagedListHolder<>(list);
        pagedListHolder.setPageSize(size);
        pagedListHolder.setPage(page);
        ResponsePageList<Book> response = new ResponsePageList<>();
        response.setNrOfElements(pagedListHolder.getNrOfElements());
        response.setPageList(pagedListHolder.getPageList());
        return response;

    }

    @Override
    public Page<Book> findPreferredBooks(String orderBy, String direction, int page, int size, UserDetails userDetails) {
//        Sort sort = null;
//        if (direction.equals("ASC")) {
//            sort = new Sort(Sort.Direction.ASC, orderBy);
//        }
//        if (direction.equals("DESC")) {
//            sort = new Sort(Sort.Direction.DESC, orderBy);
//        }
//
//        if (!(direction.equals(Direction.ASCENDING.getDirectionCode()) || direction.equals(Direction.DESCENDING.getDirectionCode()))) {
//            throw new PaginationSortingException("Invalid sort direction");
//        }
//        if (!(orderBy.equals(OrderBy.ID.getOrderByCode()) || orderBy.equals(OrderBy.TITLE.getOrderByCode()))) {
//            throw new PaginationSortingException("Invalid orderBy condition");
//        }

        User user = userService.findUserByEmail(userDetails.getUsername());
        Pageable p = PageRequest.of(page, size, new Sort(Sort.Direction.valueOf(direction), Collections.singletonList(orderBy)));

        return bookRepositoryInterface.findBookByGenresNameIn(
                user.getPreferences().stream().map(Genre::getName).collect(Collectors.toList()),
                p
        );


//      List<Genre> genreList = user.getPreferences();
//
//
//
//        Set<Book> bookSet = new HashSet<>();
//        int i;
//
//        for(i = 0; i < genreList.size(); i++){
//            Genre currentGenre = genreList.get(i);
//
//            Predicate<Genre> foundInGenre = genre -> genre.getName().toLowerCase().contains(currentGenre.getName().toLowerCase());
//            Predicate<Book> genreExist = book -> book.getGenres().stream().anyMatch(foundInGenre);
//            List<Book> list = bookRepositoryInterface.findAll(sort).stream().filter(genreExist)
//                    .collect(Collectors.toList());
//
//            bookSet.addAll(list);
//        }
//        System.out.println(bookSet);
//        PagedListHolder<Book> pagedListHolder = new PagedListHolder<>(new ArrayList<>(bookSet));
//        pagedListHolder.setPageSize(size);
//        pagedListHolder.setPage(page);
//        ResponsePageList<Book> response = new ResponsePageList<>();
//        response.setNrOfElements(pagedListHolder.getNrOfElements());
//        response.setPageList(pagedListHolder.getPageList());
//        return response;

    }
    @Override
    public ResponsePageList<Book> findSameGenreBooks(String orderBy, String direction, int page, int size, String id) {
        Sort sort = null;
        if (direction.equals("ASC")) {
            sort = new Sort(Sort.Direction.ASC, orderBy);
        }
        if (direction.equals("DESC")) {
            sort = new Sort(Sort.Direction.DESC, orderBy);
        }

        if (!(direction.equals(Direction.ASCENDING.getDirectionCode()) || direction.equals(Direction.DESCENDING.getDirectionCode()))) {
            throw new PaginationSortingException("Invalid sort direction");
        }
        if (!(orderBy.equals(OrderBy.ID.getOrderByCode()) || orderBy.equals(OrderBy.TITLE.getOrderByCode()) || orderBy.equals(OrderBy.VALUE.getOrderByCode()))) {
            throw new PaginationSortingException("Invalid orderBy condition");
        }

        Book book = bookRepositoryInterface.findBookById(Long.parseLong(id));
        List<Genre> genreList = book.getGenres();
        Set<Book> bookSet = new HashSet<>();
        int i;

        for(i = 0; i < genreList.size(); i++){
            Genre currentGenre = genreList.get(i);

            Predicate<Genre> foundInGenre = genre -> genre.getName().toLowerCase().contains(currentGenre.getName().toLowerCase());
            Predicate<Book> genreExist = b -> b.getGenres().stream().anyMatch(foundInGenre);
            List<Book> list = bookRepositoryInterface.findAll(sort).stream().filter(genreExist.and(book1 -> book1.getId() != Long.parseLong(id)))
                    .collect(Collectors.toList());

            bookSet.addAll(list);
        }
        System.out.println(bookSet);
        PagedListHolder<Book> pagedListHolder = new PagedListHolder<>(new ArrayList<>(bookSet));
        pagedListHolder.setPageSize(size);
        pagedListHolder.setPage(page);
        ResponsePageList<Book> response = new ResponsePageList<>();
        response.setNrOfElements(pagedListHolder.getNrOfElements());
        response.setPageList(pagedListHolder.getPageList());
        return response;

    }


    @ExceptionHandler(PaginationSortingException.class)
    public ResponseEntity<PagingSortingErrorResponse> exceptionHandler(Exception ex) {
        PagingSortingErrorResponse pagingSortingErrorResponse = new PagingSortingErrorResponse();
        pagingSortingErrorResponse.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
        pagingSortingErrorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(pagingSortingErrorResponse, HttpStatus.OK);
    }

    @Override
    public Book findBookByIsbn(String isbn) {
        return bookRepositoryInterface.findBookByIsbn(isbn);
    }

    @Override
    public Book findBookById(Long id) {
        return bookRepositoryInterface.findBookById(id);
    }


    public double setAverageStars(Book book) {
        book = findBookByIsbn(book.getIsbn());
        double result = 0;
        List<Rating> ratings = book.getRatings();
        int number = ratings.size();
        for (Rating i : ratings) {
            result = i.getValue() + result;
        }

        return result / number;
    }

}
