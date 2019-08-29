package com.company.library.controller;

import com.company.library.model.ImageModel;
import com.company.library.service.ImageUploadServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class ImageUploadController {

    @Autowired
    ImageUploadServiceInterface imageUploadService;

    @PostMapping("/uploadImage")
    public ResponseEntity<ImageModel> uploadImage(@RequestParam("myFile") MultipartFile file) throws IOException {
        return new ResponseEntity<>(imageUploadService.uploadImage(file), HttpStatus.OK);
    }

    @GetMapping("/getImages")
    public List<ImageModel> listAllImages(){
        return imageUploadService.getImages();
    }

    @DeleteMapping("/deleteImage")
    public void deleteImage(@RequestBody ImageModel image){
        imageUploadService.removeImage(image);
    }

    @DeleteMapping("/removeImage/{id}")
    public void removeBook(@PathVariable(value = "id") Long id) {
        imageUploadService.removeImageById(id);
    }

}
