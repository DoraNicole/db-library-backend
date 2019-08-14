package com.company.library.repository;

import com.company.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepositoryInterface extends JpaRepository<User, Long> {
    User findByEmail(String email);
    List<User> findAll();
}
