package com.company.library.service;

import com.company.library.model.ImageModel;
import com.company.library.repository.ImageUploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ImageUploadService implements ImageUploadServiceInterface {

    @Autowired
    private ImageUploadRepository imageUploadRepository;

    @Override
    public List<ImageModel> getImages() {
        return imageUploadRepository.findAll();
    }

    @Override
    public void removeImageById(Long id) {
        imageUploadRepository.deleteById(id);
    }

    @Override
    public void removeImage(ImageModel image) {
        imageUploadRepository.delete(image);
    }

    @Override
    public ImageModel uploadImage(MultipartFile file) throws IOException {
        ImageModel imageModel = new ImageModel(file.getOriginalFilename(), file.getContentType(), file.getBytes());
        imageUploadRepository.save(imageModel);
        return imageModel;
    }
}
