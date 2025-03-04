package net.san.fileud.controller;

import jakarta.servlet.http.HttpServletRequest;
import net.san.fileud.payload.UploadFileResponse;
import net.san.fileud.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;



import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class FileController {

    private static final Logger logger= LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file")MultipartFile file){
        String fileName= fileStorageService.storeFile(file);

        String fileDownloadUri= ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files){
        return Arrays.asList(files)
                .stream()
                .map(file->uploadFile(file))
                .collect(Collectors.toList());
    }



    @GetMapping("/downloadFile/{fileName:.*}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {

        // load file as Resource

        Resource resource=fileStorageService.loadFileAsResource(fileName);

        String contentType=null;

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception ex) {
            logger.info("Could not determine file type.");
        }

        if (contentType==null){
            contentType="application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachement; filename=\""+resource.getFilename()+"\"")
                .body(resource);

    }

}























