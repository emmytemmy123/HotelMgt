package fcmb.com.good.services.others;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.othersRequest.DocumentRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.othersResponse.DocumentResponse;
import fcmb.com.good.model.entity.others.Document;
import fcmb.com.good.repo.others.DocumentRepository;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private  final DocumentRepository documentRepository;



    @Override
    public ApiResponse<List<DocumentResponse>> getListOfDocument(int page, int size) {
        List<Document> documentList = documentRepository.findAll(PageRequest.of(page,size)).toList();
        if(documentList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(documentList, DocumentResponse.class));
    }

    @Override
    public ApiResponse<String> addDocument(DocumentRequest request) {
        Document document = Mapper.convertObject(request,Document.class);
        document=documentRepository.save(document);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record added successfully");
    }

    @Override
    public ApiResponse<DocumentResponse> getDocumentById(UUID documentId) {
        Optional<Document> document = documentRepository.findByUuid(documentId);

        if(document.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        Document dm = document.get();
        return new ApiResponse<DocumentResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(), Mapper.convertObject(dm,DocumentResponse.class));

    }

    private Document validateDocument(UUID uuid){
        Optional<Document> document = documentRepository.findByUuid(uuid);
        if(document.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return document.get();
    }

    @Override
    public ApiResponse<String> updateDocument(UUID documentId, DocumentRequest request) {
        Document document = validateDocument(documentId);
        document.setType(request.getType());
        document.setName(request.getName());
//        document.setSize(request.getSize());
//        document.setDescription(request.getDescription());

        document = documentRepository.save(document);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record added Successfully");
    }

    @Override
    public ApiResponse<String> deleteDocument(UUID documentId) {
        Document document = validateDocument(documentId);
        documentRepository.delete(document);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }


}
