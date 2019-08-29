package com.company.library.controller;

import com.company.library.model.Author;
import com.company.library.model.ResponsePageList;
import com.company.library.service.AuthorServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorController {

    @Autowired
    private AuthorServiceInterface authorService;

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addAuthors")
    public List<Author> addAuthors(@RequestBody List<Author> authorList) {
        return authorService.addAuthors(authorList);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/removeAuthor/{id}")
    public void deleteAuthor(@PathVariable(value = "id") Long id) {
        authorService.deleteAuthorById(id);
    }

    @GetMapping("/findAuthorByName")
    public ResponsePageList<Author> findAuthorByNameOrLastName(
            @RequestParam("orderBy") String orderBy,
            @RequestParam("direction") String direction,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("query") String query
    ) {
        return authorService.findAuthorByName(orderBy, direction, page, size, query);
    }

    @GetMapping("/checkIfAuthorExist")
    public boolean checkIfAuthorExist(String query) {
        return authorService.checkIfAuthorExist(query);
    }

    @GetMapping("/getAuthorByName")
    public Author getAuthorByName(String query) {
        return authorService.getAuthorByName(query);
    }

    @GetMapping("/getAuthorsList")
    public List<Author> getAuthorsList(){
        return authorService.getAuthorsList();
    }

}
