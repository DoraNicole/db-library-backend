package com.company.library.controller;

import com.company.library.exceptions.UserHasPenaltiesException;
import com.company.library.model.UserBook;
import com.company.library.service.UserBookServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserBookController {

    @Autowired
    UserBookServiceInterface userBookService;

    @GetMapping("/userBooks")
    @ResponseBody
    public List<UserBook> get() {
        return userBookService.getUserBooks();
    }

    @PostMapping("/addUserBook")
    public void addUserBook(@RequestBody UserBook userBook) throws UserHasPenaltiesException {
        userBookService.addUserBook(userBook);
    }

    @DeleteMapping("/removeUserBook/{id}")
    public void removeUserBook(@PathVariable(value = "id") Long id){
        userBookService.remove(id);
    }
}
