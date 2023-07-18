package com.giang.security.file_storage.service;

import com.giang.security.file_storage.entity.FileData;
import com.giang.security.file_storage.entity.ImageData;
import com.giang.security.file_storage.repository.FileDataRepository;
import com.giang.security.file_storage.repository.ImageRepository;
import com.giang.security.file_storage.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;


@Service
public class FileStorageServiceImpl implements  FileStorageService{

    @Autowired
    private FileDataRepository fileDataRepository;

    @Autowired
    private ImageRepository imageRepository;


    private final  String FOLDER_PATH = "/D/Workspace/myFiles/";

    @Override
    public String uploadImage(MultipartFile file) throws IOException {

        ImageData imageData = imageRepository.save(ImageData.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .imageData(ImageUtils.compressImage(file.getBytes()))
                .build());

        if(imageData != null) {
            return "File upload successfully : " + file.getOriginalFilename();
        }

        return null;
    }

    @Override
    public byte[] downloadImage(String fileName) {
        Optional<ImageData> dbImageData = imageRepository.findByName(fileName);

        byte[] images = ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }

    @Override
    public String uploadImageToFileSystem(MultipartFile file) throws IOException {
        String filePath = FOLDER_PATH + file.getOriginalFilename();

        FileData fileData = fileDataRepository.save(FileData.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .filePath(filePath)
                .build());

        file.transferTo(new File(filePath));

        if(fileData != null){
            return "File upload successfully : " + filePath;

        }

        return null;
    }

    @Override
    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
       Optional<FileData> fileData = fileDataRepository.findByName(fileName);
       String filePath  = fileData.get().getFilePath();
       byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }
}
