package com.company.library.service;

import com.company.library.model.UserBook;
import com.company.library.repository.UserBookRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBookService implements UserBookServiceInterface {
    @Autowired
    private UserBookRepositoryInterface userBookRepositoryInterface;

    @Override
    public void addUserBook(UserBook userBook) {
        userBookRepositoryInterface.save(userBook);
    }

    @Override
    public List<UserBook> getUserBooks() {
        return userBookRepositoryInterface.findAll();
    }
    @Override
    public void remove(Long userBookId){
        userBookRepositoryInterface.deleteById(userBookId);
    }
}
