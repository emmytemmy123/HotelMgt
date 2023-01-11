package fcmb.com.good.serviceImpl;

import fcmb.com.good.model.entity.user.ImageData;
import fcmb.com.good.repo.file.FileRepo;
import fcmb.com.good.services.StorageService;
import fcmb.com.good.utills.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class StorageServiceImpl implements StorageService {
    @Autowired
    FileRepo fileRepo;
    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        ImageData image = fileRepo.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());

         if(image != null) {
             //return a message showing successfully saved and the name of the saved file;
             return "File uploaded successfully " + file.getOriginalFilename();
         }
                return null;
    }

    @Override
    public byte[] downloadImage(String imageName) {
      Optional<ImageData> fileToDownload = fileRepo.findByName(imageName);
    byte [] image =   ImageUtils.decompressImage(fileToDownload.get().getImageData());
    return image;
    }
}
