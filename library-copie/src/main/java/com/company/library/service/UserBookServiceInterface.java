package com.company.library.service;

import com.company.library.model.UserBook;

import java.util.List;

public interface UserBookServiceInterface {
    void addUserBook(UserBook userBook);

    List<UserBook> getUserBooks();

    void remove(Long userBookId);
}
