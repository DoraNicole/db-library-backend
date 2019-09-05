package com.company.library.service;

import com.company.library.exceptions.BookOutOfStock;
import com.company.library.exceptions.UserHasPenaltiesException;
import com.company.library.model.ResponsePageList;
import com.company.library.model.UserBook;

import java.util.List;

public interface UserBookServiceInterface {

    void addUserBook(UserBook userBook) throws UserHasPenaltiesException, BookOutOfStock;
    List<UserBook> getUserBooks();
    void remove(Long userBookId);
    void sendReminder();
    ResponsePageList<UserBook> getBorrowedBooks(String orderBy, String direction, int page, int size, String id);
    void returnBorrowBook(Long userId, Long bookId);
    void changeUserBookPenalty(Long userBookId);
}
