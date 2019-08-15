package com.company.library.service;

import com.company.library.model.ImageModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageUploadServiceInterface {
    void addImageModel(ImageModel imageModel);

    List<ImageModel> getImages();

    void removeImage(Long imageModelId);

    ImageModel uploadImage(MultipartFile file) throws IOException;
}
