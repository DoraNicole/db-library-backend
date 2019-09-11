package com.company.library;

import com.company.library.model.Role;
import com.company.library.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(99)
public class RoleData implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleData(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(roleRepository.findByName("ROLE_USER")==null) {
            roleRepository.save(new Role(1L, "Read permission", "ROLE_USER"));
        }

        if(roleRepository.findByName("ROLE_ADMIN")==null) {
            roleRepository.save(new Role(2L, "Write permission", "ROLE_ADMIN"));
        }
    }
}
