package com.company.library.service;
import com.company.library.model.User;
import com.company.library.repository.UserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService implements UserServiceInterface {

    @Autowired
    UserRepositoryInterface userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

}
