package com.giang.security.file_storage.repository;

import com.giang.security.file_storage.entity.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileDataRepository extends  JpaRepository<FileData, Long>{
    Optional<FileData> findByName(String name);
}
