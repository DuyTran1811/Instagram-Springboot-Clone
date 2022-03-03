package com.example.mediaservice.repository;

import com.example.mediaservice.module.ImageMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageMetadataRepository extends MongoRepository<ImageMetadata, String> {

}
