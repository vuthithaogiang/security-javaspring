package com.giang.security.file_storage.repository;

import com.giang.security.file_storage.entity.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageData, Long> {
    Optional<ImageData> findByName(String name);
}
