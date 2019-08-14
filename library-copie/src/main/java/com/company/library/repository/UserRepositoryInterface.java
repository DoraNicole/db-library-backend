package com.company.library.repository;

import com.company.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepositoryInterface extends JpaRepository<User, Long> {
    User findByEmail(String email);
    List<User> findAll();
    User findByUsername(String username);
}
