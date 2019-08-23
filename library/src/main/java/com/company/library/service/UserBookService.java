package com.company.library.service;

import com.company.library.exceptions.UserHasPenaltiesException;
import com.company.library.model.Penalty;
import com.company.library.model.UserBook;
import com.company.library.repository.UserBookRepositoryInterface;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserBookService implements UserBookServiceInterface {

    @Autowired
    private UserBookRepositoryInterface userBookRepositoryInterface;

    @Override
    public void addUserBook(UserBook userBook) throws UserHasPenaltiesException {

        userBook.getUser().clearOldPenalties();
        //check if user has penalties
        if(userBook.getUser().getPenalties().size() < Penalty.maxNumberOfPenalties)
        userBookRepositoryInterface.save(userBook);
        else
            //throws exception and doesn`t save userBook instance if user has 2 penalties
            throw new UserHasPenaltiesException();

    }

    @Override
    public List<UserBook> getUserBooks() {
        return userBookRepositoryInterface.findAll();
    }

    @Override
    public void remove(Long userBookId){

        //check if we should penalise the user
        userBookRepositoryInterface.findById(userBookId).ifPresent(t-> {
            LocalDate returnDate = t.getReturn_date(); // get supposed return date
            if(LocalDate.now().isAfter(returnDate)) // check if real return date is after supposed return date
            {
                // penalise user with one more penalty
                       t.getUser().addPenalty(new Penalty(LocalDate.now()));
            }
        });


        userBookRepositoryInterface.deleteById(userBookId);
    }
}
