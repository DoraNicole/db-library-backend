package com.company.library.service;

import com.company.library.DTO.Registration;
import com.company.library.exceptions.EmailExistsException;
import com.company.library.model.User;
import com.company.library.repository.UserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    UserRepositoryInterface userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    //ordinary user
    @Override
    public User registerNewUserAccount(Registration userObject) throws EmailExistsException{
        if (emailExists(userObject.getEmail())) {
            throw new EmailExistsException("There is an account with that email adress: " + userObject.getEmail());
        }

        final User user = new User();

        user.setFirstName(userObject.getFirstName());
        user.setLastName(userObject.getLastName());
        user.setPassword(passwordEncoder.encode(userObject.getPassword()));
        user.setEmail(userObject.getEmail());
       // user.setUsing2FA(userObject.isUsing2FA());
        user.setAdmin(false);
        return userRepository.save(user);
    }

    @Override
    public boolean emailExists(final String email) {
        User foundUser = userRepository.findByEmail(email);
        System.out.println(foundUser);
        return (userRepository.findByEmail(email) != null);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }


}
