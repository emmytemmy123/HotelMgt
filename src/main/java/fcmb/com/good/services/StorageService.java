package fcmb.com.good.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    String uploadImage(MultipartFile file) throws IOException;
    byte[] downloadImage(String imageName);
}
