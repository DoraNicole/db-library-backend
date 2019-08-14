package com.company.library.service;

import com.company.library.DTO.RegistrationDTO;
import com.company.library.repository.UserRepositoryInterface;
import com.company.library.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserServiceInterface userService;
    private final UserRepositoryInterface userRepository;
    private final PasswordEncoder bcryptEncoder;

    @Autowired
    public JwtUserDetailsService (
            UserServiceInterface userService,
            UserRepositoryInterface userRepository,
            PasswordEncoder bcryptEncoder
    ) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.bcryptEncoder = bcryptEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new RuntimeException();
        }
        else
        {
            User u=new User();
            u.setEmail(user.getEmail());
            u.setPassword(user.getPassword());
            return (UserDetails) u;
        }
    }

    public User save(RegistrationDTO user) {
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userRepository.save(newUser);
    }
}