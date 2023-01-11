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

    @PostMapping
    public ResponseEntity<?> uploadFile(@RequestParam("image")MultipartFile file) throws IOException {
       String uploadImageResponse = storageService.uploadImage(file);
       return  ResponseEntity.status( HttpStatus.OK).body(uploadImageResponse);
    }
    @GetMapping("/{filename}")
    public ResponseEntity<?> downloadFile(@PathVariable("filename") String fileName){
       byte [] downloadedFile =  storageService.downloadImage(fileName);
       return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png"))
               .body(downloadedFile);
    }
}
