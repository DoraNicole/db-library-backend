package com.company.library.exceptions;

public class BookOutOfStock extends Throwable {

    @Override
    public String getMessage() {
        return "The book is out of stock.";
    }
}
