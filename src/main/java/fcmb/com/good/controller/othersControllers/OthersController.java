package fcmb.com.good.controller.othersControllers;


import fcmb.com.good.model.dto.request.othersRequest.DocumentRequest;
import fcmb.com.good.model.dto.request.othersRequest.HotelRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.othersResponse.DocumentResponse;
import fcmb.com.good.model.dto.response.othersResponse.HotelResponse;
import fcmb.com.good.services.others.DocumentService;
import fcmb.com.good.services.others.HotelService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static fcmb.com.good.utills.EndPoints.OthersEndPoints.*;;
import static fcmb.com.good.utills.EndpointParam.*;
import static fcmb.com.good.utills.EndpointParam.SIZE_DEFAULT;

@RestController
@RequestMapping(others)
@RequiredArgsConstructor
public class OthersController {

    private final DocumentService documentService;
    private final HotelService hotelService;


                                    //FIND_LISTS_OF_DOCUMENT AND HOTEL
    @GetMapping(FIND_DOCUMENT)
    @ApiOperation(value = "Endpoint for retrieving lists of document", response = DocumentResponse.class, responseContainer = "List")
    public ApiResponse<List<DocumentResponse>> getListOfDocument(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                 @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return documentService.getListOfDocument(page,size);
    }

    @GetMapping(FIND_HOTEL)
    @ApiOperation(value = "Endpoint for retrieving lists of hotel", response = HotelResponse.class, responseContainer = "List")
    public ApiResponse<List<HotelResponse>> getListOfHotel(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                           @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return hotelService.getListOfHotel(page,size);
    }


                                    //ADD_HOTEL AND DOCUMENT
    @PostMapping(ADD_DOCUMENT)
    @ApiOperation(value = "Endpoint for adding new document to database", response = String.class)
    public ApiResponse<String> addDocument(@Valid @RequestBody DocumentRequest request) {
        return documentService.addDocument(request);
    }

    @PostMapping(ADD_HOTEL)
    @ApiOperation(value = "Endpoint for adding new hotel to database", response = String.class)
    public ApiResponse<String> addHotel(@Valid @RequestBody HotelRequest request) {
        return hotelService.addHotel(request);
    }


                                        //FIND_DOCUMENTS AND HOTEL_BY_ID
    @GetMapping(FIND_DOCUMENT_BY_ID)
    @ApiOperation(value = "Endpoint for fetching document by id from database", response = DocumentResponse.class)
    public ApiResponse<DocumentResponse> getDocumentById(@PathVariable(value = "id") UUID document_id) {
        return documentService.getDocumentById(document_id);
    }

    @GetMapping(FIND_HOTEL_BY_ID)
    @ApiOperation(value = "Endpoint for fetching hotel by id from database", response = HotelResponse.class)
    public ApiResponse<HotelResponse> getHotelById(@PathVariable(value = "id") UUID hotel_id) {
        return hotelService.getHotelById(hotel_id);
    }


                                            //UPDATE_HOTEL AND DOCUMENT
    @PutMapping(UPDATE_DOCUMENT)
    @ApiOperation(value = "Endpoint for updating document by id from database", response = String.class)
    public ApiResponse<String> updateDocument(@PathVariable(value = "id") UUID document_id,
                                              @RequestBody DocumentRequest request) {
        return documentService.updateDocument(document_id, request);
    }

    @PutMapping(UPDATE_HOTEL)
    @ApiOperation(value = "Endpoint for updating hotel by id from database", response = String.class)
    public ApiResponse<String> updateHotel(@PathVariable(value = "id") UUID hotel_id,
                                           @RequestBody HotelRequest request) {
        return hotelService.updateHotel(hotel_id, request);
    }


                                         //DELETE_HOTEL AND DOCUMENT
    @DeleteMapping(DELETE_DOCUMENT)
    @ApiOperation(value = "Endpoint for deleting document by id from database", response = String.class)
    public ApiResponse<String> deleteDocument(@PathVariable(value = "id") UUID document_id) {
        return documentService.deleteDocument(document_id);
    }

    @DeleteMapping(DELETE_HOTEL)
    @ApiOperation(value = "Endpoint for deleting hotel by id from database", response = String.class)
    public ApiResponse<String> deleteHotel(@PathVariable(value = "id") UUID hotel_id) {
        return hotelService.deleteHotel(hotel_id);
    }


}
