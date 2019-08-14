package com.company.library.service;

import com.company.library.DTO.RegistrationDTO;
import com.company.library.model.User;
import com.company.library.repository.UserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserServiceInterface, UserDetailsService {

    @Autowired
    UserRepositoryInterface userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    //ordinary user
    @Override
    public User registerNewUserAccount(RegistrationDTO userObject)

    {
        if (emailExists(userObject.getEmail())) {
            throw new IllegalArgumentException("There is an account with that email adress: " + userObject.getEmail());
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


    @Override
    public User loadUserByEmail(String mail) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(mail);
        if (user == null) {
            throw new RuntimeException();
        }
        else
        {
            User u=new User();
            u.setEmail(user.getEmail());
            u.setPassword(user.getPassword());
            return u;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
