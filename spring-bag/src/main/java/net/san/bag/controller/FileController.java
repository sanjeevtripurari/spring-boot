package net.san.bag.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;

@RestController
public class FileController {

    //Uploading file

    @RequestMapping(value="/upload", method= RequestMethod.POST)
    public String uploadFile(@RequestParam("file")MultipartFile file){
        String filePath=System.getProperty("user.dir")+"/uploads"+ File.separator+file.getOriginalFilename();
        String fileUploadStatus;

        try {

            // Creating an object of FileOutputSteam
            FileOutputStream fout = new FileOutputStream(filePath);
            fout.write(file.getBytes());
            fout.close();
            fileUploadStatus="File Uploaded Successfully";

        } catch (Exception e) {
            e.printStackTrace();
            fileUploadStatus="Error in uploading file:"+e;
        }
        return fileUploadStatus;
    }

    // Getting list of filenames that have been uploaded
    @RequestMapping(value="/getfiles",method = RequestMethod.GET)
    public String[] getFiles(){
        String folderPath=System.getProperty("user.dir")+"/Uploads";
        File directory=new File(folderPath);

        String[] filenames=directory.list();
        return filenames;
    }

    // Downloading a file
    @RequestMapping(value = "/download/{path:.*}", method = RequestMethod.GET)
    public ResponseEntity downloadFile(@PathVariable("path") String filename) throws FileNotFoundException{
        // Checking whether the file requested for download
        String fileUploadpath=System.getProperty("user.dir")+"/Uploads";

        String[] filenames = this.getFiles();
        boolean contains = Arrays.asList(filenames).contains(filename);
        if (!contains){
            return new ResponseEntity("File not found", HttpStatus.NOT_FOUND);
        }

        // Setting up the filepath
        String filePath=fileUploadpath+File.separator+filename;

        // Creating new file instance
        File file=new File(filePath);

        // Creating a new InputStreamResource
        InputStreamResource resource=new InputStreamResource(new FileInputStream(file));

        // Creating a new instance of HttpHeaeders
        HttpHeaders headers=new HttpHeaders();

        // Setting up values for contenttype
        String contentType="application/octet-stream";
        String headerValue="attachment; filename=\"" + resource.getFilename()+"\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);

    }

}
















