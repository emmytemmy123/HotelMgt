package fcmb.com.good.serviceImpl;

import fcmb.com.good.model.entity.file.FileData;
import fcmb.com.good.model.entity.file.ImageData;
import fcmb.com.good.model.entity.settings.enums.Identification;
import fcmb.com.good.repo.file.FileDataRepo;
import fcmb.com.good.repo.file.FileRepo;
import fcmb.com.good.services.StorageService;
import fcmb.com.good.utills.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class StorageServiceImpl implements StorageService {
    @Autowired
    FileRepo fileRepo;
    @Autowired
    FileDataRepo fileDataRepo;

    private final String FILE_PATH = "C:\\Users\\USER\\Desktop\\spring image";
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
    public String uploadImageToFile(MultipartFile file) throws IOException {
        String filePath = FILE_PATH + file.getOriginalFilename();
FileData fileData = fileDataRepo.save(FileData.builder()
                            .name(file.getOriginalFilename())
                            .type(file.getContentType())
                            .filePath(filePath).build());
        file.transferTo(new File(filePath));
        if(fileData != null) {
            //return a message showing successfully saved and the name of the saved file;
            return "File uploaded successfully to" + filePath;
        }
        return null;

    }

    @Override
    public byte[] downloadImage(String imageName) {
      Optional<ImageData> fileToDownload = fileRepo.findByName(imageName);
    byte [] image =   ImageUtils.decompressImage(fileToDownload.get().getImageData());
    return image;
    }

    @Override
    public byte[] downloadImageToFile(String fileName) throws IOException {
        Optional<FileData> fileData = fileDataRepo.findByName(fileName);
        String filePath = fileData.get().getFilePath();
        byte [] image = Files.readAllBytes(new File(filePath).toPath());
        return image;
    }
}
