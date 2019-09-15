package com.company.library;

import com.company.library.DTO.Registration;
import com.company.library.exceptions.EmailExistsException;
import com.company.library.model.Role;
import com.company.library.model.User;
import com.company.library.repository.RoleRepository;
import com.company.library.repository.UserRepositoryInterface;
import com.company.library.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    UserRepositoryInterface userRepositoryInterface;

    @Mock
    UserService userService2;

    @Mock
    RoleRepository roleRepository;

    @Mock
    // @Autowired
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;


    public UserServiceTest() {
    }

    @Test
    public void registerNewUserAccountTest() throws EmailExistsException {
//        Registration userDTO = new Registration();
//        userDTO.setFirstName("Maria");
//        userDTO.setLastName("Dumitrescu");
//        userDTO.setEmail("mariaadumitresscu05@gmail.com");
//        userDTO.setPassword("maria123");
//
//        User newUser = new User();
//        newUser.setFirstName("Maria");
//        newUser.setLastName("Dumitrescu");
//        newUser.setEmail("mariaadumitrescu05@gmail.com");
//        newUser.setPassword("maria123");
//        newUser.setAdmin(false);
//        Role userRole = new Role();
//        userRole.setName("ROLE_USER");
//        newUser.setRoles(Collections.singleton(userRole));
//        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//        newUser.setGenres(null);
//
//       // when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("maria123");
//
//
//
//        when(userService2.emailExists(userDTO.getEmail())).thenReturn(false);
//        when(roleRepository.findByName("ROLE_USER")).thenReturn(userRole);
//        when(userRepositoryInterface.save(newUser)).thenReturn(newUser);
//
//        assertEquals(newUser, userService.registerNewUserAccount(userDTO));
    }
}
