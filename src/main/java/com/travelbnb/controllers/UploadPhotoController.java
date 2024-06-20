package com.travelbnb.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/uploadPhotos")
public class UploadPhotoController {

    @PostMapping("/photo")
    public String uploadPhoto()
    {
        return "photo added successfully";
    }
}
