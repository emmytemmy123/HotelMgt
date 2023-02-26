package fcmb.com.good.controller.othersControllers;

import fcmb.com.good.services.others.UploadService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/uploader")
@RequiredArgsConstructor
public class FileUploaderController {

   private final UploadService uploadService;

    @PostMapping("/upload/files")
    @ApiOperation(value = "Make a POST request to upload the file",
            produces = "application/json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadFile(
            //@ApiParam(name = "merchantid", value = "The unique id of the user that has the file", required = true)
            //@RequestParam(value = "merchantid", required = true) String merchantid,
            //@RequestParam(value = "types", required = true) String types,
            @ApiParam(name = "file", value = "Select the file to Upload", required = true)
            @RequestPart("file") MultipartFile file
    ) throws IOException {
       return uploadService.uploadFile( file);

    }

    @ApiOperation(value = "Endpoint for downloading file")
    @GetMapping("/file/download")
    public StreamingResponseBody downloadPhoto(@RequestParam("filename") String name,
                                           @RequestParam(value="display", defaultValue = "attachment")String display,
                                           HttpServletResponse response) throws IOException {
    return uploadService.loadPhoto(name, display, response);
    }


    @ApiOperation(value = "Endpoint for previewing downloading file")
    @GetMapping("/file/preview")
    public StreamingResponseBody previewPhoto(@RequestParam("filename") String name, HttpServletResponse response) throws IOException {
        return uploadService.previewFile(name,  response);
    }

//    @PostMapping("/upload")
//    @ApiOperation(value = "Make a POST request to upload the file",
//            produces = "application/json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity uploadDocument(
//           // @ApiParam(name = USER_TOKEN, value = "The unique id of the user that has the file", required = true)
//            //@RequestParam(value = USER_TOKEN, required = true) String userToken,
//            //@RequestParam(value = "types", required = true) String types,
//            @ApiParam(name = "file", value = "Select the file to Upload", required = true)
//            @RequestPart("file") MultipartFile file) throws IOException {
//      return uploadService.uploadDocument(file);
//    }


}

