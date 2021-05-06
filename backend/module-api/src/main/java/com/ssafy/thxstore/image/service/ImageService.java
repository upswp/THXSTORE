package com.ssafy.thxstore.image.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {
    private final AwsS3Service awsS3Service;
    public static final String DOMAIN_NAME = "thxstore.s3.ap-northeast-2.amazonaws.com";

    public String createImage(MultipartFile image) throws IOException {
        String imgName = awsS3Service.uploadImage(image);
        String imgPath = "https://" + DOMAIN_NAME + "/" + imgName;

        return imgPath;
    }
}