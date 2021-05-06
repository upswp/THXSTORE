package com.ssafy.thxstore.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.thxstore.image.config.AwsConfiguration;
import com.ssafy.thxstore.image.property.AwsS3Property;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AwsS3Service {
    private final AwsConfiguration awsConfiguration;
    private final AwsS3Property awsS3Property;
    private AmazonS3 amazonS3;

    @Transactional
    public String uploadImage(MultipartFile image) throws IOException {
        amazonS3 = awsConfiguration.setS3Client();
        UUID uuid = UUID.randomUUID();
        long time = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
        String imageName = uuid + "-" + formatter.format(time) + image.getOriginalFilename();


        File uploadFile = convert(image)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

        amazonS3.putObject(new PutObjectRequest(awsS3Property.getBucket(), imageName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        removeNewFile(uploadFile);
        return imageName;
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    private void removeNewFile(File targetFile) {
        targetFile.delete();
    }
}