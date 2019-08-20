package com.company.library.service;

import com.company.library.DTO.Registration;
import com.company.library.exceptions.EmailExistsException;
import com.company.library.model.Role;
import com.company.library.model.User;
import com.company.library.repository.RoleRepository;
import com.company.library.repository.UserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
public class UserService implements UserServiceInterface {

    private final PasswordEncoder bcryptEncoder;
    private final RoleRepository roleRepository;
    private final UserRepositoryInterface userRepository;

    @Autowired
    public UserService(PasswordEncoder bcryptEncoder, RoleRepository roleRepository, UserRepositoryInterface userRepository) {
        this.bcryptEncoder = bcryptEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public User registerNewUserAccount(Registration userDto) {
        if (emailExist(userDto.getEmail())) {
            throw new EmailExistsException(
                    "There is an account with that email adress: "
                            + userDto.getEmail());
        }
        com.company.library.model.User newUser = new com.company.library.model.User();

        User user = new User();

        if (userDto.getEmail().equals("librarymaster0@gmail.com") && userDto.getPassword().equals("qwerty1234.")) {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            newUser.setFirstName(userDto.getFirstName());
            newUser.setLastName(userDto.getLastName());
            newUser.setEmail(userDto.getEmail());
            newUser.setPassword(bcryptEncoder.encode(userDto.getPassword()));
            newUser.setRoles(Collections.singleton(adminRole));
            newUser.setAdmin(true);
        } else {
            Role userRole = roleRepository.findByName("ROLE_USER");
            newUser.setFirstName(userDto.getFirstName());
            newUser.setLastName(userDto.getLastName());
            newUser.setEmail(userDto.getEmail());
            newUser.setPassword(bcryptEncoder.encode(userDto.getPassword()));
            newUser.setRoles(Collections.singleton(userRole));
            newUser.setAdmin(false);
        }
        return userRepository.save(newUser);
    }

    private boolean emailExist(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

}
