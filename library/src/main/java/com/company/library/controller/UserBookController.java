package com.company.library.controller;

import com.company.library.exceptions.BookNotFoundException;
import com.company.library.exceptions.UserHasPenaltiesException;
import com.company.library.exceptions.UserNotFoundException;
import com.company.library.model.Book;
import com.company.library.model.User;
import com.company.library.model.UserBook;
import com.company.library.service.BookService;
import com.company.library.service.BookServiceInterface;
import com.company.library.service.UserBookServiceInterface;
import com.company.library.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserBookController {

    @Autowired
    UserBookServiceInterface userBookService;

    @Autowired
    UserServiceInterface userService;

    @Autowired
    BookServiceInterface bookService;


    @GetMapping("/userBooks")
    @ResponseBody
    public List<UserBook> get() {
        return userBookService.getUserBooks();
    }


    //request only id, not whole object for book and user
    @PostMapping("/addUserBook")
    public void addUserBook(@RequestParam long user_id, @RequestParam long book_id) throws UserHasPenaltiesException, UserNotFoundException, BookNotFoundException {

            UserBook userBook = new UserBook();
            //check if book_id exist in our database
            Book book =   bookService.findById(book_id).orElseThrow(BookNotFoundException::new);
            //check if user_id exist in our database
            User user =   userService.findById(user_id).orElseThrow(UserNotFoundException::new);

            userBookService.addUserBook(userBook);
        }


    @DeleteMapping("/removeUserBook/{id}")
    public void removeUserBook(@PathVariable(value = "id") Long id){
        userBookService.remove(id);
    }
}
