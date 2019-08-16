package com.company.library.service;

import com.company.library.DTO.Registration;
import com.company.library.exceptions.EmailExistsException;
import com.company.library.model.User;
import com.company.library.model.UserInterface;
import com.company.library.repository.UserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInterface userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private UserRepositoryInterface userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                new ArrayList<>());
    }

    public User save(Registration user) throws EmailExistsException {
        User newUser = new User();
        if(emailExists(user.getEmail())) {
            throw new EmailExistsException("this email already exists!");
        }
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));

        return userDao.save(newUser);
    }


    public boolean emailExists(final String email) {
        User foundUser = userRepository.findByEmail(email);
        System.out.println(foundUser);
        return (userRepository.findByEmail(email) != null);
    }
}
