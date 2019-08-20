package com.company.library.service;

import com.company.library.DTO.Registration;
import com.company.library.exceptions.EmailExistsException;
import com.company.library.model.Role;
import com.company.library.model.User;
import com.company.library.repository.RoleRepository;
import com.company.library.repository.UserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepositoryInterface userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepositoryInterface userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.company.library.model.User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        Collection<GrantedAuthority> roles = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), roles);
    }

    public User saveNewPassword(String email, String password) {

        User foundUser = userRepository.findByEmail(email);
            foundUser.setPassword(bcryptEncoder.encode(password));
            return userDao.save(foundUser);

    }

    public boolean emailExists(final String email) {
        com.company.library.model.User foundUser = userRepository.findByEmail(email);
        System.out.println(foundUser);
        return (userRepository.findByEmail(email) != null);
    }
}
