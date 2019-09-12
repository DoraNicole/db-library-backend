package com.company.library;

import com.company.library.model.Role;
import com.company.library.model.User;
import com.company.library.repository.RoleRepository;
import com.company.library.repository.UserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Component
@Order(98)
public class AdminData implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final PasswordEncoder bcryptEncoder;
    private final UserRepositoryInterface userRepositoryInterface;

    @Autowired
    public AdminData(RoleRepository roleRepository, PasswordEncoder bcryptEncoder, UserRepositoryInterface userRepositoryInterface) {
        this.roleRepository = roleRepository;
        this.bcryptEncoder = bcryptEncoder;
        this.userRepositoryInterface = userRepositoryInterface;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        if (userRepositoryInterface.findByEmail("librarymaster0@gmail.com") == null) {

            //"Super", "Admin", "librarymaster0@gmail.com", bcryptEncoder.encode("qwerty1234."), Collections.singleton(adminRole), true, true
            User newUser = new User();
            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            newUser.setFirstName("Super");
            newUser.setLastName("Admin");
            newUser.setEmail("librarymaster0@gmail.com");
            newUser.setPassword(bcryptEncoder.encode("qwerty1234."));
            newUser.setRoles(Collections.singleton(adminRole));
            newUser.setAdmin(true);
            newUser.setEnabled(true);
            userRepositoryInterface.save(newUser);

        }
    }
}
