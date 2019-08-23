package com.company.library.model;


import com.company.library.service.BookServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.OptionalDouble;

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

//    @NotBlank
//    @OneToMany(fetch = FetchType.LAZY)
    private String genres;

    @OneToOne(fetch = FetchType.LAZY)
    private ImageModel img;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Rating> ratings;

    private double averageStars;




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

    public ImageModel getImg() {
        return img;
    }

    public void setImg(ImageModel img) {
        this.img = img;
    }


    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public double getAverageStars() {
        averageStars = setAverageStars();
        return averageStars;
    }

    public double setAverageStars() {
        double result = 0;
        List<Rating> ratings = getRatings();
        int number = ratings.size();
        for (Rating i : ratings) {
            result = i.getValue() + result;
        }


        return (result / number);
    }
}
