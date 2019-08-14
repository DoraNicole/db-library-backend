package com.company.library.service;

import com.company.library.DTO.Registration;
import com.company.library.exceptions.EmailExistsException;
import com.company.library.model.User;

import java.util.List;

public interface UserServiceInterface {

    User registerNewUserAccount(Registration userObject) throws EmailExistsException;

    boolean emailExists(final String email);

    List<User> findAll();
}
