package com.example.mediaservice.controllers;


import com.example.mediaservice.module.ImageMetadata;
import com.example.mediaservice.payload.UploadFileResponse;
import com.example.mediaservice.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Objects;

@Slf4j
@RestController
public class ControllerFileUpload {

    private final ImageService imageService;

    public ControllerFileUpload(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/images")
    @PreAuthorize("hasRole('USER')")
    public UploadFileResponse uploadFile(@RequestParam("image") MultipartFile file, @AuthenticationPrincipal Principal principal) {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        log.info("received a request to upload file {} for user {}", filename, principal.getName());

        ImageMetadata metadata = imageService.upload(file, principal.getName());

        return new UploadFileResponse(metadata.getFilename(), metadata.getUri(), metadata.getFileType());
    }

}
