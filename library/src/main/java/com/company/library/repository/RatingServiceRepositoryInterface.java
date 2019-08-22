package com.company.library.repository;

import com.company.library.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingServiceRepositoryInterface extends JpaRepository<Rating, Long> {


}
