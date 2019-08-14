package com.company.library.service;

import com.company.library.DTO.RegistrationDTO;
import com.company.library.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserServiceInterface {

    User registerNewUserAccount(RegistrationDTO userObject);

    boolean emailExists(final String email);

    List<User> findAll();

    User loadUserByEmail(String username) throws UsernameNotFoundException;

}
