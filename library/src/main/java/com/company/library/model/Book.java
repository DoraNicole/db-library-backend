package com.company.library.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String isbn;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String publishingHouse;

    private Integer year;

    @NotBlank
    @OneToMany(fetch = FetchType.LAZY)
    private List<Genre> genres;

    @OneToOne(fetch = FetchType.LAZY)
    private ImageModel img;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<Genre> getGenre() {
        return genres;
    }

    public void setGenre(List<Genre> genres) {
        this.genres = genres;
    }

    public ImageModel getImg() {
        return img;
    }

    public void setImg(ImageModel img) {
        this.img = img;
    }
}
