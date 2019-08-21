package com.company.library.service;

import com.company.library.exceptions.UserHasPenaltiesException;
import com.company.library.model.UserBook;

import java.util.List;
import java.util.Optional;

public interface UserBookServiceInterface {

    void addUserBook(UserBook userBook) throws UserHasPenaltiesException;
    List<UserBook> getUserBooks();
    void remove(Long userBookId);
    Optional<UserBook> findById(Long userId);

}
