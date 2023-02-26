package fcmb.com.good.services.others;

import fcmb.com.good.model.dto.request.othersRequest.DocumentRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.othersResponse.DocumentResponse;
import fcmb.com.good.model.entity.others.Document;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface DocumentService {

    ApiResponse<List<DocumentResponse>> getListOfDocument(int page, int size);

    ApiResponse<String> addDocument(DocumentRequest request);

    ApiResponse<DocumentResponse> getDocumentById(UUID documentId);

    ApiResponse<String> updateDocument(UUID documentId, @RequestBody DocumentRequest request);

    ApiResponse<String> deleteDocument(UUID documentId);




}
