package com.company.library.exceptions;

public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
        super("User not found exception");
    }
}
