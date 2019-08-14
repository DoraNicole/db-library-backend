package com.company.library.configuration.jwt;

import com.company.library.DTO.Registration;
import com.company.library.repository.UserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepositoryInterface userRepository;
    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.company.library.model.User requestedUser = userRepository.findByEmail(username);

        if (requestedUser == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new User(requestedUser.getEmail(), requestedUser.getPassword(), new ArrayList<>());
    }

    public com.company.library.model.User save(Registration user) {
        com.company.library.model.User newUser = new com.company.library.model.User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        return userRepository.save(newUser);
    }
}
