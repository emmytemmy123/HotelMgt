package fcmb.com.good.services.serviceRender;


import fcmb.com.good.model.dto.request.servicesRequest.SubServiceRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.servicesResponse.SubServiceResponse;

import java.util.List;
import java.util.UUID;

public interface SubServiceService {

    ApiResponse<List<SubServiceResponse>> getListOfSubService(int page, int size);

    ApiResponse<String> addSubService(SubServiceRequest request);

    ApiResponse<SubServiceResponse> getSubServiceById(UUID subServiceId);

    ApiResponse<String> updateSubService(UUID subServiceId, SubServiceRequest request);

    ApiResponse<String> deleteSubService(UUID subServiceId);

    ApiResponse<List<SubServiceResponse>> searchSubServiceByName(String serviceName);


}
