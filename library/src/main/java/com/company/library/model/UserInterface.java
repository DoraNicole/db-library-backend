package com.company.library.model;

import org.springframework.data.repository.CrudRepository;

public interface UserInterface  extends CrudRepository<User, Integer> {

    User findByEmail(String email);
}
