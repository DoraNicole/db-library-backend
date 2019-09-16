package com.company.library.repository;

import com.company.library.model.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBookRepositoryInterface extends JpaRepository<UserBook,Long> {
}
