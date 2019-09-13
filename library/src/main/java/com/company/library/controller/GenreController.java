package com.company.library.controller;

import com.company.library.model.Genre;
import com.company.library.model.ResponsePageList;
import com.company.library.service.GenreServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GenreController {

    @Autowired
    private GenreServiceInterface genreService;

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addGenres")
    public List<Genre> addGenres(@RequestBody List<Genre> genreList) {
        return genreService.addGenres(genreList);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/removeGenre/{id}")
    public void deleteGenre(@PathVariable(value = "id") Long id) {
        genreService.deleteGenreById(id);
    }

    @GetMapping("/findGenreByName")
    public ResponsePageList<Genre> findGenreByName(
            @RequestParam("orderBy") String orderBy,
            @RequestParam("direction") String direction,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("query") String query
    ) {
        return genreService.findGenreByName(orderBy, direction, page, size, query);
    }

    @GetMapping("/checkIfGenreExist")
    public boolean checkIfGenreExist(String query) {
        return genreService.checkIfGenreExist(query);
    }

    @GetMapping("/getGenreByName")
    public Genre getGenreByName(String query) {
        return genreService.getGenreByName(query);
    }

    @GetMapping("/getGenresList")
    public List<Genre> getGenresList() {
        List<Genre> lista = genreService.getGenresList().stream().distinct().collect(Collectors.toList());
        return lista;
    }

}
