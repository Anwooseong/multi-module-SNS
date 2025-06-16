package com.social.service;

import com.social.IntegrationTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

class AmazonS3ServiceTest extends IntegrationTestSupport {

    @Autowired
    private AmazonS3Service amazonS3Service;

    static List<String> s3Urls;

    @AfterEach
    void tearDown() {
        s3Urls.forEach(fileName -> {
                    amazonS3Service.deleteFile(fileName);
                });
    }

    @DisplayName("S3 이미지 리스트 업로드")
    @Test
    void uploadFile() throws Exception{
        // given
        String fileName = "default_profile";
        String contentType = "png";
        String filePath = "src/test/resources/static/image/default-profile.png";
        MockMultipartFile multipartFile = getMockMultipartFile(
                fileName, contentType, filePath
        );
        // when
        s3Urls = amazonS3Service.uploadFile(List.of(multipartFile, multipartFile));

        // then
        Assertions.assertThat(s3Urls.size()).isEqualTo(2);
    }

    private MockMultipartFile getMockMultipartFile(String fileName, String contentType, String filePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        return new MockMultipartFile("files", fileName + "." + contentType, contentType, fileInputStream);
    }

}