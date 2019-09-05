package com.company.library.controller;

import com.company.library.exceptions.BookOutOfStock;
import com.company.library.exceptions.UserHasPenaltiesException;
import com.company.library.model.Book;
import com.company.library.model.ResponsePageList;
import com.company.library.model.User;
import com.company.library.model.UserBook;
import com.company.library.service.EmailService;
import com.company.library.service.UserBookServiceInterface;
import com.company.library.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserBookController {

    @Autowired
    private UserBookServiceInterface userBookService;

    @Autowired
    UserServiceInterface userService;

    @Autowired
    EmailService emailService;

    @GetMapping("/userBooks")
    @ResponseBody
    public List<UserBook> get() {
        return userBookService.getUserBooks();
    }

    @PostMapping("/addUserBook")
    public void addUserBook(@RequestBody UserBook userBook) throws UserHasPenaltiesException, BookOutOfStock {

        userBookService.addUserBook(userBook);
        User user= userBook.getUser();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Book borrowing notification");
        message.setText(String.format("Good choice, my friend. Enjoy your read! You can return it until "+ userBook.getReturn_date().toString()));
        emailService.sendEmail(message);

    }

    @GetMapping("/reminder")
    public void reminder(){
        userBookService.sendReminder();
    }

    @DeleteMapping("/removeUserBook/{id}")
    public void removeUserBook(@PathVariable(value = "id") Long id){
        userBookService.remove(id);
    }

    @GetMapping("/getBorrowedBooks")
    public ResponsePageList<UserBook> getBorrowedBooks(
            @RequestParam("orderBy") String orderBy,
            @RequestParam("direction") String direction,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("id") String id
    ){
        return userBookService.getBorrowedBooks(orderBy, direction, page, size, id);
    }

    @PostMapping("/returnBook")
    public void returnBook(@RequestParam("id") Long userId, @RequestBody Book book){
        userBookService.returnBorrowBook(userId, book.getId());

    }
}
