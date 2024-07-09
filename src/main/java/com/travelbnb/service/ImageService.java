package com.travelbnb.service;

import com.travelbnb.entity.AppUserEntity;
import com.travelbnb.payloads.ImagePayload;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
     ImagePayload uploadFile(MultipartFile file, String bucketName, long propertyId, AppUserEntity user);

}
