package com.company.library.controller;

import com.company.library.DTO.ChartObject;
import com.company.library.DTO.StatusChart;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        emailService.sendBorrowEmail(userBook);

//        User user= userBook.getUser();
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(user.getEmail());
//        message.setSubject("Book borrowing notification");
//        message.setText(String.format("Good choice, my friend. Enjoy your read! You can return it until "+ userBook.getReturn_date().toString()));
//        emailService.sendEmail(message);

    }

    @GetMapping("/reminder")
    public void reminder(){
        userBookService.sendReminder();
    }

    @DeleteMapping("/removeUserBook/{id}")
    public void removeUserBook(@PathVariable(value = "id") Long id){
        userBookService.removeById(id);
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
    public void returnBook(@RequestParam("id") Long userId){
        userBookService.returnBorrowBook(userId);
    }

    @GetMapping("/populateChart")
    public ResponseEntity<List<ChartObject>> populateChart(){
        return new ResponseEntity<>(userBookService.populateChart(), HttpStatus.OK);
    }

    @GetMapping("/populateStatus")
    public ResponseEntity<List<StatusChart>> populateStatus(){
        return new ResponseEntity<>(userBookService.populateStatusChart(), HttpStatus.OK);

    @PostMapping("/sendAlmostReturnDateEmail")
    public void sendAlmostReturnEmail(){
        userBookService.sendReminder();

    }
}
