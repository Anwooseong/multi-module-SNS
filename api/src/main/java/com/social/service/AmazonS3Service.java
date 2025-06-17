package com.social.service;

import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AmazonS3Service {
//    @Value("${cloud.aws.s3.bucketName}")

    private final S3Template s3Template;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${spring.cloud.aws.region.static}")
    private String region;

    public List<String> uploadFile(List<MultipartFile> multipartFiles) {
        if (multipartFiles == null || multipartFiles.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "업로드할 파일이 비어있습니다.");
        }

        return multipartFiles.stream()
                .map(file -> {
                    // 각 파일에 대한 업로드 로직
                    String originalFilename = file.getOriginalFilename();
                    if (originalFilename == null || originalFilename.isEmpty()) {
                        // 파일 이름이 없는 경우 처리 (예: 건너뛰거나 예외 발생)
                        // 여기서는 예외를 발생시키겠습니다.
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 이름이 유효하지 않습니다.");
                    }

                    String generatedFileName = createFileName(originalFilename);

                    try (InputStream is = file.getInputStream()) {
                        S3Resource upload = s3Template.upload(bucket, generatedFileName, is);
                        return upload.getURL().toString();
                    } catch (IOException | S3Exception e) {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다: " + originalFilename, e);
                    } catch (Exception e) {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 처리 중 오류 발생: " + originalFilename, e);
                    }
                })
                .toList();
    }

    public String createFileName(String fileName){
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName){
        try{
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일" + fileName + ") 입니다.");
        }
    }


    public void deleteFile(String fileName) {
        try {
            s3Template.deleteObject(bucket, fileName);
        } catch (S3Exception e) {
            throw new RuntimeException("파일을 찾을 수 없습니다.", e);
        }
    }
}
