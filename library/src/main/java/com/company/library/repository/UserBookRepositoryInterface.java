package com.company.library.repository;

import com.company.library.model.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBookRepositoryInterface extends JpaRepository<UserBook,Long> {
}
