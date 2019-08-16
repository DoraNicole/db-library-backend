package com.company.library.service;

import com.company.library.DTO.Registration;
import com.company.library.model.User;

import java.util.List;

public interface UserServiceInterface {

    List<User> findAll();

}
