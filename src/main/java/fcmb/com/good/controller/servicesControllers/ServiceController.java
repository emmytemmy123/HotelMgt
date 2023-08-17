package fcmb.com.good.controller.servicesControllers;


import fcmb.com.good.model.dto.request.servicesRequest.SubServiceRequest;
import fcmb.com.good.model.dto.request.servicesRequest.SubServiceRequest2;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.servicesResponse.SubServiceRequestResponse;
import fcmb.com.good.model.dto.response.servicesResponse.SubServiceResponse;
import fcmb.com.good.services.serviceRender.SubServiceRequestService;
import fcmb.com.good.services.serviceRender.SubServiceService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static fcmb.com.good.utills.EndPoints.ServiceEndPoints.*;
import static fcmb.com.good.utills.EndpointParam.*;
import static fcmb.com.good.utills.EndpointParam.SIZE_DEFAULT;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(service)
@RequiredArgsConstructor
public class ServiceController {

    private final SubServiceService subServiceService;
    private final SubServiceRequestService subServiceRequestService;



                                    //FIND_LISTS_OF_SERVICES

    @GetMapping(FIND_SUB_SERVICE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') or hasAuthority('ROLE_USER') ")
    @ApiOperation(value = "Endpoint for retrieving lists of subService", response = SubServiceResponse.class, responseContainer = "List")
    public ApiResponse<List<SubServiceResponse>> getListOfSubService(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                               @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return subServiceService.getListOfSubService(page,size);
    }

    @GetMapping(FIND_SUB_SERVICE_REQUEST)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') or hasAuthority('ROLE_USER') ")
    @ApiOperation(value = "Endpoint for retrieving lists of subServiceRequest", response = SubServiceRequestResponse.class, responseContainer = "List")
    public ApiResponse<List<SubServiceRequestResponse>> getListOfSubServiceRequest(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                     @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return subServiceRequestService.getListOfSubServiceRequest(page,size);
    }



                                    //ADD_SERVICES

    @PostMapping(ADD_SUB_SERVICE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for adding new subService to database", response = String.class)
    public ApiResponse<String> addSubService(@RequestBody SubServiceRequest request) {
        return subServiceService.addSubService(request);
    }

    @PostMapping(ADD_SUB_SERVICE_REQUEST)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for adding new subServiceREQUEST to database", response = String.class)
    public ApiResponse<String> addSubServiceRequest(@RequestBody SubServiceRequest2 request) {
        return subServiceRequestService.addSubServiceRequest(request);
    }


                                                //FIND_SERVICES_ID

    @GetMapping(FIND_SUB_SERVICE_BY_ID)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') or hasAuthority('ROLE_USER') ")
    @ApiOperation(value = "Endpoint for fetching subService by id from database", response = SubServiceResponse.class)
    public ApiResponse<SubServiceResponse> getSubServiceById(@PathVariable(value = "id") UUID subService_id) {
        return subServiceService.getSubServiceById(subService_id);

    }

    @GetMapping(FIND_SUB_SERVICE_REQUEST_BY_ID)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') or hasAuthority('ROLE_USER') ")
    @ApiOperation(value = "Endpoint for fetching subServiceRequest by id from database", response = SubServiceRequestResponse.class)
    public ApiResponse<SubServiceRequestResponse> getSubServiceRequestById(@PathVariable(value = "id") UUID subServiceRequestId) {
        return subServiceRequestService.getSubServiceRequestById(subServiceRequestId);

    }



                                                //UPDATE_SERVICE

    @PutMapping(UPDATE_SUB_SERVICE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for updating subService by id from database", response = String.class)
    public ApiResponse<String> updateSubService(@PathVariable(value = "id") UUID subService_id,
                                             @RequestBody SubServiceRequest request) {
        return subServiceService.updateSubService(subService_id, request);
    }

    @PutMapping(UPDATE_SUB_SERVICE_REQUEST)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for updating subServiceRequest by id from database", response = String.class)
    public ApiResponse<String> updateSubServiceRequest(@PathVariable(value = "id") UUID subServiceRequestId,
                                                @RequestBody SubServiceRequest2 request) {
        return subServiceRequestService.updateSubServiceRequest(subServiceRequestId, request);
    }



                                            //DELETE SERVICES

    @DeleteMapping(DELETE_SUB_SERVICE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for deleting subService by id from database", response = String.class)
    public ApiResponse<String> deleteSubService(@PathVariable(value = "id") UUID subService_id) {
        return subServiceService.deleteSubService(subService_id);
    }

    @DeleteMapping(DELETE_SUB_SERVICE_REQUEST)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for deleting subServiceRequest by id from database", response = String.class)
    public ApiResponse<String> deleteSubServiceRequest(@PathVariable(value = "id") UUID subServiceRequestId) {
        return subServiceRequestService.deleteSubServiceRequest(subServiceRequestId);
    }



                                                 //FIND_SUB_SERVICE_BY_NAME

    @GetMapping(SEARCH_SUB_SERVICE_BY_NAME)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') or hasAuthority('ROLE_USER') ")
    @ApiOperation(value = "Endpoint for retrieving lists of SUB_SERVICE by serviceName", response = SubServiceResponse.class, responseContainer = "List")
    public ApiResponse<List<SubServiceResponse>> searchListOfSubServiceByName(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                              @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size,
                                                                              @RequestParam String serviceName) {
        return subServiceService.searchSubServiceByName(serviceName);
    }










}
