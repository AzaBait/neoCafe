package com.neobis.neoCafe.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

    String uploadImage(MultipartFile file);


    void deleteImage(String publicId);

    String extractPublicId(String imageUrl);
}
