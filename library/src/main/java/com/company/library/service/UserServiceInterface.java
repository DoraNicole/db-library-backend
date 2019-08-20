package com.company.library.service;

import com.company.library.DTO.Registration;
import com.company.library.exceptions.EmailExistsException;
import com.company.library.model.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {

    List<User> findAll();
    void save(User user);
    Optional<User> findById(Long userId);
    User registerNewUserAccount(Registration userDto) throws EmailExistsException;
    void delete(User user);

    User findUserByEmail(String email);
}
