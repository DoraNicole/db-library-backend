package com.company.library.exceptions;

public class UserHasPenaltiesException extends Exception {
    @Override
    public String getMessage() {

        return "User has reached the number of maximum penalties and cannot book more books!";
    }
}
