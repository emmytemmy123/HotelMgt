package fcmb.com.good.services.others;

import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.entity.others.Document;
import fcmb.com.good.repo.others.DocumentRepository;
import fcmb.com.good.utills.MessageUtil;
import fcmb.com.good.utills.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UploadServiceImpl implements UploadService {

    private final DocumentRepository documentRepository;
    private final ServletContext servletContext;

    @Value("${FILE_UPLOAD_LOCATION}")
    private String uploadLocation;

    @Override
    public ResponseEntity uploadFile( MultipartFile file) throws IOException {

        String files = Utils.baseDir(uploadLocation).getPath() + File.separator + file.getOriginalFilename().replaceAll("[\\\\/><\\|\\s\"'{}()\\[\\]]+", "_");
        File testFile = new File(files);
        FileUtils.writeByteArrayToFile(testFile, file.getBytes());

//        List<Document> documentList = new ArrayList<>();
//        Document document = new Document();
//        document.setName(testFile.getName());
//        documentList.add(document);

        Document document = new Document();
        document.setName(file.getOriginalFilename());
        document.setType(file.getContentType());
        document.setSize(String.valueOf(file.getSize()));
        document.setDescription(file.getName());
        document.setFilePath(files);

        file.transferTo(new File(files));

        documentRepository.save(document);
        return ResponseEntity.ok(new ApiResponse<String>(MessageUtil.SUCCESS, "files Upload Successfully"));

    }


    @Override
    public StreamingResponseBody loadPhoto(String name, String display, HttpServletResponse response) throws FileNotFoundException {
        String fileDownloadUri = Utils.baseDir(uploadLocation).getPath() + "/"  + name;
        log.info("*************************** File LOCATION{}", fileDownloadUri);
        File file = new File(fileDownloadUri);
        log.info("*************************** File name {}", name);
        String path = "";
        if (file.exists()) {
            path = file.getAbsolutePath();
        } else {
            file = new File(Utils.baseDir(uploadLocation).getPath() + File.separator  + "/default.png");
            path = file.getAbsolutePath();

        }
        name = file.getName();

        InputStream in = new FileInputStream(file);
        response.setHeader("Cache-Control",
                "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        response.setContentType(servletContext.getMimeType(path));
        //  String fileExtension=
        response.setHeader("Content-Disposition",
                display+"; filename=" + name);
        return outputStream -> {
            int nRead;
            byte[] data = new byte[in.available()];
            while ((nRead = in.read(data, 0, data.length)) != -1) {
                outputStream.write(data, 0, nRead);
            }
            outputStream.flush();
            outputStream.close();
        };

    }

    @Override
    public StreamingResponseBody previewFile(String name, HttpServletResponse response) throws FileNotFoundException {
        log.info("*************************** File Reading{}", name);
        String fileDownloadUri = Utils.baseDir(uploadLocation).getPath() + "/"  + name;
        File file = new File(fileDownloadUri);
        String path = "";
        if (file.exists()) {
            path = file.getAbsolutePath();
        } else {
            file = new File(Utils.baseDir(uploadLocation).getPath() + File.separator  + "/default.png");
            path = file.getAbsolutePath();

        }
        name = file.getName();

        InputStream in = new FileInputStream(file);
        response.setHeader("Cache-Control",
                "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        response.setContentType(servletContext.getMimeType(path));
        //  String fileExtension=
        response.setHeader("Content-Disposition",
                "inline; filename=" + name);
        return outputStream -> {
            int nRead;
            byte[] data = new byte[in.available()];
            while ((nRead = in.read(data, 0, data.length)) != -1) {
                outputStream.write(data, 0, nRead);
            }
            outputStream.flush();
            outputStream.close();
        };
    }


//    @Override
//    public ResponseEntity uploadDocument(String userToken, String types, MultipartFile file) throws IOException {
////        Accounts acct = serviceHelper.validateAccount(userToken);
////        String files = Utils.baseDir(uploadDir).getPath() + File.separator + file.getOriginalFilename().replaceAll("[\\\\/><\\|\\s\"'{}()\\[\\]]+", "_");
////        File testFile = new File(files);
////        FileUtils.writeByteArrayToFile(testFile, file.getBytes());
////
////        List<FilesData> r = new ArrayList<>();
////        FilesData d = new FilesData();
////        d.setFilesName(testFile.getName());
////        d.setTypes(types);
////        r.add(d);
////        acct.setFiles(MerchantUtil.getFileRequest(acct.getFiles(), r));
////        accountsRepo.save(acct);
////        return ResponseEntity.ok(new ApiResponse<>(MessageUtil.SUCCESS, OKAY,testFile.getName()));
//        return null;
//    }


}