package com.social.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "이미지 업로드(게시글 등록 전, 프로필 관련)")
public interface AmazonS3ControllerSpec {

    @Operation(summary = "이미지 업로드")
    ResponseEntity<List<String>> uploadFile(
            List<MultipartFile> multipartFiles
    );

    @Operation(summary = "이미지 삭제")
    @Parameter(name = "fileName", description = "파일명", example = "test.jpg", required = true)
    ResponseEntity<String> deleteFile(String fileName);
}
