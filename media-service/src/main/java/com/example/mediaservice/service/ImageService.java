package com.example.mediaservice.service;

import com.example.mediaservice.module.ImageMetadata;
import com.example.mediaservice.repository.ImageMetadataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
@Slf4j
public class ImageService {

    private final FileStorageService fileStorageService;

    private final ImageMetadataRepository imageMetadataRepository;

    public ImageService(FileStorageService fileStorageService, ImageMetadataRepository imageMetadataRepository) {
        this.fileStorageService = fileStorageService;
        this.imageMetadataRepository = imageMetadataRepository;
    }


    public ImageMetadata upload(MultipartFile file, String username) {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        log.info("storing file {}", filename);

        ImageMetadata metadata = fileStorageService.store(file, username);
        return imageMetadataRepository.save(metadata);
    }
}
