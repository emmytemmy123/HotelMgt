package fcmb.com.good.controller.servicesControllers;


import fcmb.com.good.model.dto.request.servicesRequest.SubServiceRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.servicesResponse.SubServiceResponse;
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
@RequestMapping(service)
@RequiredArgsConstructor
public class ServiceController {

    private final SubServiceService subServiceService;


                                    //FIND_LISTS_OF_SERVICES



    @GetMapping(FIND_SUB_SERVICE)
    @ApiOperation(value = "Endpoint for retrieving lists of subService", response = SubServiceResponse.class, responseContainer = "List")
    public ApiResponse<List<SubServiceResponse>> getListOfSubService(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                               @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return subServiceService.getListOfSubService(page,size);
    }


                                    //ADD_SERVICES



    @PostMapping(ADD_SUB_SERVICE)
    @ApiOperation(value = "Endpoint for adding new subService to database", response = String.class)
    public ApiResponse<String> addSubService(@RequestBody SubServiceRequest request) {
        return subServiceService.addSubService(request);
    }


                                                //FIND_SERVICES_ID



    @GetMapping(FIND_SUB_SERVICE_BY_ID)
    @ApiOperation(value = "Endpoint for fetching subService by id from database", response = SubServiceResponse.class)
    public ApiResponse<SubServiceResponse> getSubServiceById(@PathVariable(value = "id") UUID subService_id) {
        return subServiceService.getSubServiceById(subService_id);

    }



                                                //UPDATE_SERVICE



    @PutMapping(UPDATE_SUB_SERVICE)
    @ApiOperation(value = "Endpoint for updating subService by id from database", response = String.class)
    public ApiResponse<String> updateSubService(@PathVariable(value = "id") UUID subService_id,
                                             @RequestBody SubServiceRequest request) {
        return subServiceService.updateSubService(subService_id, request);
    }


                                            //DELETE SERVICES



    @DeleteMapping(DELETE_SUB_SERVICE)
    @ApiOperation(value = "Endpoint for deleting subService by id from database", response = String.class)
    public ApiResponse<String> deleteSubService(@PathVariable(value = "id") UUID subService_id) {
        return subServiceService.deleteSubService(subService_id);
    }


                                        //FIND_SUB_SERVICE_BY_ROOM



                                                 //FIND_SUB_SERVICE_BY_NAME

    @GetMapping(SEARCH_SUB_SERVICE_BY_NAME)
    @ApiOperation(value = "Endpoint for retrieving lists of SUB_SERVICE by serviceName", response = SubServiceResponse.class, responseContainer = "List")
    public ApiResponse<List<SubServiceResponse>> searchListOfSubServiceByName(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                              @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size,
                                                                              @RequestParam String serviceName) {
        return subServiceService.searchSubServiceByName(serviceName);
    }










}
