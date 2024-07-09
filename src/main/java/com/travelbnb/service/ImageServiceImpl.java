package com.travelbnb.service;

import com.travelbnb.entity.AppUserEntity;
import com.travelbnb.entity.Image;
import com.travelbnb.entity.PropertyEntity;
import com.travelbnb.exception.NotFoundException;
import com.travelbnb.payloads.ImagePayload;
import com.travelbnb.repository.ImageRepository;
import com.travelbnb.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService{

    private final PropertyRepository propertyRepository;
    private final ImageRepository imageRepository;
    private final BucketService bucketService;

    @Autowired
    public ImageServiceImpl(PropertyRepository propertyRepository, ImageRepository imageRepository, BucketService bucketService) {
        this.propertyRepository = propertyRepository;
        this.imageRepository = imageRepository;
        this.bucketService = bucketService;
    }

    @Override
    public ImagePayload uploadFile(MultipartFile file, String bucketName, long propertyId, AppUserEntity user) {
        Optional<PropertyEntity> opPropertyId = propertyRepository.findById(propertyId);
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }

        if (opPropertyId.isEmpty()) {
            throw new NotFoundException("Property not found with ID: " + propertyId);
        }

        String imgUrl = bucketService.uploadFile(file,bucketName);
        PropertyEntity property = opPropertyId.get();

        if(user == null){
            throw new NotFoundException("User not found");
        }

        Image image = new Image();
        image.setImageUrl(imgUrl);
        image.setPropertyEntity(property);
        Image saved = imageRepository.save(image);
        ImagePayload impd = entityToPayload(saved);

        return impd;
    }



//    conversion Entity To Payload
    private ImagePayload entityToPayload(Image img){
        ImagePayload imd = new ImagePayload();
        imd.setId(img.getId());
        imd.setImageUrl(img.getImageUrl());
        imd.setPropertyEntity(img.getPropertyEntity());

        return imd;
    }
}
