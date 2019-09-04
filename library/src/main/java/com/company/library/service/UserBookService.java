package com.company.library.service;

import com.company.library.exceptions.BookOutOfStock;
import com.company.library.exceptions.UserHasPenaltiesException;
import com.company.library.model.Book;
import com.company.library.model.Penalty;
import com.company.library.model.User;
import com.company.library.model.UserBook;
import com.company.library.repository.BookRepositoryInterface;
import com.company.library.repository.UserBookRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserBookService implements UserBookServiceInterface {

    @Autowired
    private UserBookRepositoryInterface userBookRepositoryInterface;

    @Autowired
    private BookServiceInterface bookService;

    @Autowired
    private UserServiceInterface userService;

    @Autowired
    EmailService emailService;

    @Override
    public void addUserBook(UserBook userBook) throws UserHasPenaltiesException, BookOutOfStock {

        //check if we dont have any more books of this type
        //we use bookService because we want to check the stack value from database, not from the request we sended
        if (bookService.findBookById(userBook.getBook().getId()).getStock() <= 0)
            throw new BookOutOfStock();


        if (userBook.getUser().getPenalties().size() < Penalty.maxNumberOfPenalties){

            //when we loan a book the stock should decrease with one unit
            //we use bookService so the value will change in database
            bookService.findBookById(userBook.getBook().getId()).decreseStock();
            userBookRepositoryInterface.save(userBook);
    }

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

        //when the book is returned, the stock should increase with one unit
        userBookRepositoryInterface.findById(userBookId).ifPresent(t->t.getBook().setStock(t.getBook().getStock() + 1));
        userBookRepositoryInterface.deleteById(userBookId);
    }

    //@Scheduled(cron = "0 * * * * ?")
    public void sendReminder(){
        List<UserBook> list=userBookRepositoryInterface.remindUsers();
        for(UserBook u: list) {
            User user=u.getUser();
            SimpleMailMessage messageReturn = new SimpleMailMessage();
            messageReturn.setTo(user.getEmail());
            messageReturn.setSubject("Book return notification");
            messageReturn.setText("Reminder to return your book. You have one day left.");
            emailService.sendEmail(messageReturn);
        }
    }

    @Override
    public List<UserBook> getBorrowedBooks(Long userId) {
            List<UserBook> returnList = new ArrayList<>();

            //search in all user-book relations for the users who has the given id and add in return list their book
           getUserBooks().forEach(t->{

                if(t.getUser().getId() == userId)
                    returnList.add(t);
            });
            return returnList;
        }

    @Override
    public void returnBorrowBook(Long userId, Long bookId) {

        //search in all userBooks for the one that has the user and the book we are looking for
        //then use remove to return book
      //  System.out.println(getUserBooks().stream().filter(t->t.getBook().getId() == bookId && t.getUser().getId() == userId).findAny().orElse(null).getId());

        remove(getUserBooks().stream().filter(t->t.getBook().getId() == bookId && t.getUser().getId() == userId).findAny().orElse(null).getId());
    }

    public void changeUserBookPenalty(Long userBookId){
        userBookRepositoryInterface.findById(userBookId).ifPresent(t->{
            t.setGeneratedPenalty(true);
            userBookRepositoryInterface.saveAndFlush(t);
        });
    }
}

