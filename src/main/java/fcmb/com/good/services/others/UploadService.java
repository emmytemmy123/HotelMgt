package fcmb.com.good.services.others;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface UploadService {

    ResponseEntity uploadFile( MultipartFile file) throws IOException;

    StreamingResponseBody loadPhoto(String name, String display, HttpServletResponse response) throws FileNotFoundException;

    StreamingResponseBody previewFile(String name, HttpServletResponse response) throws FileNotFoundException;

//    ResponseEntity uploadDocument(String userToken, MultipartFile file) throws IOException;
//
//    ResponseEntity uploadDocument(String userToken, String types, MultipartFile file) throws IOException;

}


