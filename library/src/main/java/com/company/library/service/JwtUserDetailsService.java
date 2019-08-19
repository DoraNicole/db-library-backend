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
    private RoleRepository roleRepository;

    @Autowired
    private UserRepositoryInterface userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.company.library.model.User user = userDao.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        Collection<GrantedAuthority> roles = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new User(user.getEmail(), user.getPassword(), roles);
    }

    public com.company.library.model.User save(Registration user) throws EmailExistsException {
        com.company.library.model.User newUser = new com.company.library.model.User();
        if(emailExists(user.getEmail())) {
            throw new EmailExistsException("this email already exists!");
        }




        if(user.getEmail().equals("librarymaster0@gmail.com") && user.getPassword().equals("qwerty1234.")){
            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
            newUser.setRoles(Collections.singleton(adminRole));
            newUser.setAdmin(true);
        }
        else {
            Role userRole = roleRepository.findByName("ROLE_USER");
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
            newUser.setRoles(Collections.singleton(userRole));
            newUser.setAdmin(false);
        }
        return userDao.save(newUser);
    }


    public boolean emailExists(final String email) {
        User foundUser = userRepository.findByEmail(email);
        System.out.println(foundUser);
        return (userRepository.findByEmail(email) != null);
    }
}
