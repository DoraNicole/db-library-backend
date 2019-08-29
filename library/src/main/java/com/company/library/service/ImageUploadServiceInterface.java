package com.company.library.service;

import com.company.library.model.ImageModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageUploadServiceInterface {

    List<ImageModel> getImages();
    void removeImageById(Long id);
    void removeImage(ImageModel image);
    ImageModel uploadImage(MultipartFile file) throws IOException;
}
