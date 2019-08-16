package com.company.library.repository;


import com.company.library.model.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageUploadRepository extends JpaRepository<ImageModel, Long> {
}