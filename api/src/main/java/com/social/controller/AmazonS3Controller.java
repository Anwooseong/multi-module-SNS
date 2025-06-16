package com.social.controller;

import com.social.service.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/file")
public class AmazonS3Controller implements AmazonS3ControllerSpec{

    private final AmazonS3Service amazonS3Service;

    @Override
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<String>> uploadFile(@RequestPart("files") List<MultipartFile> multipartFiles){
        return ResponseEntity.ok(amazonS3Service.uploadFile(multipartFiles));
    }

    @Override
    @DeleteMapping
    public ResponseEntity<String> deleteFile(@RequestParam("fileName") String fileName){
        amazonS3Service.deleteFile(fileName);
        return ResponseEntity.ok(fileName);
    }
}