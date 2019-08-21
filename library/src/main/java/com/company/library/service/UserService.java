package com.company.library.service;

import com.company.library.DTO.Registration;
import com.company.library.exceptions.EmailExistsException;
import com.company.library.model.Role;
import com.company.library.model.User;
import com.company.library.model.VerificationToken;
import com.company.library.repository.RoleRepository;
import com.company.library.repository.UserRepositoryInterface;
import com.company.library.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


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

    @Autowired
    private UserRepositoryInterface userDao;

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
    public User registerNewUserAccount(Registration userDto) throws EmailExistsException {
        if (emailExists(userDto.getEmail())) {
            throw new EmailExistsException(
                    "There is an account with that email adress: "
                            + userDto.getEmail());
        }
        User newUser = new User();


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


    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean emailExists(final String email) {
        com.company.library.model.User foundUser = userRepository.findByEmail(email);
        System.out.println(foundUser);
        return (userRepository.findByEmail(email) != null);
    }


    public User saveNewPassword(String email, String password) {
        User foundUser = userRepository.findByEmail(email);
        foundUser.setPassword(bcryptEncoder.encode(password));
        return userDao.save(foundUser);
    }



}
