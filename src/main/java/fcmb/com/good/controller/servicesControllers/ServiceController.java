package fcmb.com.good.controller.servicesControllers;


import fcmb.com.good.model.dto.request.servicesRequest.ServiceRequestRequest;
import fcmb.com.good.model.dto.request.servicesRequest.ServicesRequest;
import fcmb.com.good.model.dto.request.servicesRequest.SubServiceRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.servicesResponse.ServiceRequestResponse;
import fcmb.com.good.model.dto.response.servicesResponse.ServiceResponse;
import fcmb.com.good.model.dto.response.servicesResponse.SubServiceResponse;
import fcmb.com.good.services.serviceRender.ServiceRequestService;
import fcmb.com.good.services.serviceRender.SubServiceService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static fcmb.com.good.utills.EndPoints.ServiceEndPoints.*;
import static fcmb.com.good.utills.EndpointParam.*;
import static fcmb.com.good.utills.EndpointParam.SIZE_DEFAULT;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceRequestService serviceRequestService;
    private final SubServiceService subServiceService;


                                    //FIND_LISTS_OF_SERVICES
    @GetMapping(FIND_SERVICE_REQUEST)
    @ApiOperation(value = "Endpoint for retrieving list of serviceRequest", response = ServiceRequestResponse.class, responseContainer = "List")
    public ApiResponse<List<ServiceRequestResponse>> getListOfServiceRequest(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                             @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return serviceRequestService.getListOfServiceRequest(page,size);
    }


    @GetMapping(FIND_SUB_SERVICE)
    @ApiOperation(value = "Endpoint for retrieving lists of subService", response = SubServiceResponse.class, responseContainer = "List")
    public ApiResponse<List<SubServiceResponse>> getListOfSubService(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                               @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return subServiceService.getListOfSubService(page,size);
    }


                                    //ADD_SERVICES
    @PostMapping(ADD_SERVICE_REQUEST)
    @ApiOperation(value = "Endpoint for adding new serviceRequest to database", response = String.class)
    public ApiResponse<String> addServiceRequest(@Valid @RequestBody ServiceRequestRequest request) {
        return serviceRequestService.addServiceRequest(request);
    }


    @PostMapping(ADD_SUB_SERVICE)
    @ApiOperation(value = "Endpoint for adding new subService to database", response = String.class)
    public ApiResponse<String> addSubService(@RequestBody SubServiceRequest request) {
        return subServiceService.addSubService(request);
    }


                                                //FIND_SERVICES_ID
    @GetMapping(FIND_SERVICE_REQUEST_BY_ID)
    @ApiOperation(value = "Endpoint for fetching serviceRequest by id from database", response = ServiceRequestResponse.class)
    public ApiResponse<ServiceRequestResponse> getServiceRequestById(@PathVariable(value = "id") UUID serviceRequest_id) {
        return serviceRequestService.getServiceRequestById(serviceRequest_id);
    }


    @GetMapping(FIND_SUB_SERVICE_BY_ID)
    @ApiOperation(value = "Endpoint for fetching subService by id from database", response = SubServiceResponse.class)
    public ApiResponse<SubServiceResponse> getSubServiceById(@PathVariable(value = "id") UUID subService_id) {
        return subServiceService.getSubServiceById(subService_id);

    }



                                                //UPDATE_SERVICE
    @PutMapping(UPDATE_SERVICE_REQUEST)
    @ApiOperation(value = "Endpoint for updating serviceRequest by id from database", response = String.class)
    public ApiResponse<String> updateServiceRequest(@PathVariable(value = "id") UUID serviceRequest_id,
                                                    @RequestBody ServiceRequestRequest request) {
        return serviceRequestService.updateServiceRequest(serviceRequest_id, request);
    }


    @PutMapping(UPDATE_SUB_SERVICE)
    @ApiOperation(value = "Endpoint for updating subService by id from database", response = String.class)
    public ApiResponse<String> updateSubService(@PathVariable(value = "id") UUID subService_id,
                                             @RequestBody SubServiceRequest request) {
        return subServiceService.updateSubService(subService_id, request);
    }


                                            //DELETE SERVICES
    @DeleteMapping(DELETE_SERVICE_REQUEST)
    @ApiOperation(value = "Endpoint for deleting serviceRequest by id from database", response = String.class)
    public ApiResponse<String> deleteServiceRequest(@PathVariable(value = "id") UUID serviceRequest_id) {
        return serviceRequestService.deleteServiceRequest(serviceRequest_id);
    }


    @DeleteMapping(DELETE_SUB_SERVICE)
    @ApiOperation(value = "Endpoint for deleting subService by id from database", response = String.class)
    public ApiResponse<String> deleteSubService(@PathVariable(value = "id") UUID subService_id) {
        return subServiceService.deleteSubService(subService_id);
    }


                                        //FIND_SUB_SERVICE_BY_ROOM

    @GetMapping(SEARCH_SUB_SERVICE_BY_ROOM)
    @ApiOperation(value = "Endpoint for retrieving lists of SUB_SERVICE by Room", response = SubServiceResponse.class, responseContainer = "List")
    public ApiResponse<List<SubServiceResponse>> searchListOfSubServiceByRoom(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                              @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size,
                                                                              @RequestParam UUID roomUuid) {
        return subServiceService.searchSubServiceByRoom(roomUuid);
    }


                                                 //FIND_SUB_SERVICE_BY_NAME

    @GetMapping(SEARCH_SUB_SERVICE_BY_NAME)
    @ApiOperation(value = "Endpoint for retrieving lists of SUB_SERVICE by serviceName", response = SubServiceResponse.class, responseContainer = "List")
    public ApiResponse<List<SubServiceResponse>> searchListOfSubServiceByName(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                              @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size,
                                                                              @RequestParam String serviceName) {
        return subServiceService.searchSubServiceByName(serviceName);
    }



                                        //FIND_SUB_SERVICE_BY_CUSTOMER_AND_ROOM

    @GetMapping(SEARCH_SUB_SERVICE_BY_CUSTOMER_AND_ROOM)
    @ApiOperation(value = "Endpoint for retrieving lists of SUB_SERVICE by customerAndRoom", response = SubServiceResponse.class, responseContainer = "List")
    public ApiResponse<List<SubServiceResponse>> searchListOfSubServiceByCustomerAndRoom(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                                         @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size,
                                                                              @RequestParam UUID customerUuid, @RequestParam UUID roomUuid) {
        return subServiceService.findSubServiceByCustomerAndRoom(customerUuid, roomUuid);
    }


                                            //FIND_SUB_SERVICE_BY_NAME

    @GetMapping(SEARCH_SERVICE_REQUEST_BY_NAME)
    @ApiOperation(value = "Endpoint for retrieving lists of ServiceRequest by serviceName", response = ServiceRequestResponse.class, responseContainer = "List")
    public ApiResponse<List<ServiceRequestResponse>> searchListOfServiceRequestByName(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                              @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size,
                                                                              @RequestParam String serviceName) {
        return serviceRequestService.searchServiceRequestByName(serviceName);
    }




}
