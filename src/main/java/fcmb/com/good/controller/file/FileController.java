package fcmb.com.good.controller.file;

import fcmb.com.good.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private StorageService storageService;

    @PostMapping("/upload_image_to_db")
    public ResponseEntity<?> uploadFile(@RequestParam("image")MultipartFile file) throws IOException {
       String uploadImageResponse = storageService.uploadImage(file);
       return  ResponseEntity.status( HttpStatus.OK).body(uploadImageResponse);
    }
    @PostMapping("/upload_image_to_file")
    public ResponseEntity<?>uploadFileSystem(@RequestParam("image")MultipartFile file) throws IOException {
     String response =  storageService.uploadImageToFile(file);
     return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/{filename}")
    public ResponseEntity<?> downloadFileFromDb(@PathVariable("filename") String fileName){
       byte [] downloadedFile =  storageService.downloadImage(fileName);
       return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png"))
               .body(downloadedFile);
    }
    @GetMapping("/file/{filename}")
    public ResponseEntity<?> downloadFileSystem(@PathVariable("filename") String fileName) throws IOException {
     byte [] fileInByte =  storageService.downloadImageToFile(fileName);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png"))
                .body(fileInByte);
    }
}
