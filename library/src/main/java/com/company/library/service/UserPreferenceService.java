package com.company.library.service;

import com.company.library.model.Book;
import com.company.library.model.Genre;
import com.company.library.model.User;
import com.company.library.repository.BookRepositoryInterface;
import com.company.library.repository.UserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserPreferenceService {

    private final UserRepositoryInterface userRepository;
    private final BookRepositoryInterface bookRepository;


    @Autowired
    public UserPreferenceService(UserRepositoryInterface userRepository, BookRepositoryInterface bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }


    @Transactional
    public void save(List<Genre> preferences, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername());
        user.setPreferences(preferences);
        userRepository.save(user);
    }


    public Collection<Genre> getAllFor(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername());
        return user.getPreferences();
    }


    public Collection<Book> getPreferredBooksFor(UserDetails userDetails) {
        Collection<Genre> preferredGenres = getAllFor(userDetails);
        return bookRepository.findBookByGenresNameIn(
                preferredGenres.stream()
                .map(Genre::getName)
                .collect(Collectors.toList()),
                PageRequest.of(0, 10)
        )
                .getContent();
    }
}
