package com.company.library.controller;

import com.company.library.model.ImageModel;
import com.company.library.service.ImageUploadServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ImageUploadController {

    @Autowired
    ImageUploadServiceInterface imageUploadService;

    @PostMapping("/uploadImage")
    public ImageModel uploadImage(@RequestParam("myFile") MultipartFile file) throws IOException {
        return imageUploadService.uploadImage(file);
    }

    @GetMapping("/getImages")
    public List<ImageModel> listAllImages(){
        return imageUploadService.getImages();
    }
}
