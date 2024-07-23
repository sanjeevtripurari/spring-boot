package net.san.fileud.service;

import net.san.fileud.exception.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import net.san.fileud.property.FileStorageProperties;
import net.san.fileud.exception.MyFileNotFoundException;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    public final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties){
        this.fileStorageLocation=Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        }catch (Exception e){
            throw new FileStorageException("Couldnt crete directory"+e);
        }

    }

    public String storeFile(MultipartFile file){
        // Normalize filename
        String fileName= StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry invalid filename" + fileName);
            }
            Path targetLocation=this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;

    } catch (Exception e){
            throw new FileStorageException("Couldnt store file"+fileName);

        }
    }


    public Resource loadFileAsResource(String fileName){
        try {
            Path filePath=this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()){
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found");
            }
        } catch (MalformedURLException e) {
            throw new MyFileNotFoundException("File not found");
        }
    }
}

























