package com.company.library.repository;

import com.company.library.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    @Query("select u from VerificationToken t join t.user u where u.email = :email and t.expiryDateTime >= CURRENT_TIMESTAMP")
    VerificationToken findActiveByUserEmail(@Param("email") String email);
}
