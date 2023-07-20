package com.giang.security.demo;


import com.giang.security.file_storage.service.FileStorageService;
import jakarta.persistence.PostRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/demo-controller")
public class DemoController {


    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello!");
    }

    @PostMapping("/image")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file) throws IOException {
        String uploadImage = fileStorageService.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping("/image/{fileName}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName) {
        byte[] imageDate = fileStorageService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageDate);
    }

    @PostMapping("/fileSystem")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> uploadImageToSystem(@RequestParam("image") MultipartFile file)
            throws IOException{
        String imageData= fileStorageService.uploadImageToFileSystem(file);
    return ResponseEntity.status(HttpStatus.OK).body(imageData);
    }

    @GetMapping("/fileSystem/{fileName}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName)
        throws IOException{
        byte[] imageData = fileStorageService.downloadImageFromFileSystem(fileName);
        return  ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }
}
