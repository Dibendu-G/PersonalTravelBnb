package com.travelbnb.controllers;

import com.travelbnb.service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/bucket")
@CrossOrigin("*")
public class BucketController {


    private final BucketService bucketService;

    @Autowired
    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @PostMapping(path = "upload/file/{bucketName}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>uploadFile(@RequestParam MultipartFile file,
                                       @PathVariable String bucketName){

        return new ResponseEntity<>(bucketService.uploadFile(file,bucketName), HttpStatus.OK);

    }
}
