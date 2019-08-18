package com.company.library.repository;

import com.company.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInterface extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
