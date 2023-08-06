package fcmb.com.good.services.serviceRender;


import fcmb.com.good.model.dto.request.servicesRequest.SubServiceRequest;
import fcmb.com.good.model.dto.request.servicesRequest.SubServiceRequest2;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.servicesResponse.SubServiceRequestResponse;
import fcmb.com.good.model.dto.response.servicesResponse.SubServiceResponse;

import java.util.List;
import java.util.UUID;

public interface SubServiceRequestService {

    ApiResponse<List<SubServiceRequestResponse>> getListOfSubServiceRequest(int page, int size);

    ApiResponse<String> addSubServiceRequest(SubServiceRequest2 request);

    ApiResponse<SubServiceRequestResponse> getSubServiceRequestById(UUID subServiceRequestId);

    ApiResponse<String> updateSubServiceRequest(UUID subServiceRequestId, SubServiceRequest2 request);

    ApiResponse<String> deleteSubServiceRequest(UUID subServiceRequestId);

    ApiResponse<List<SubServiceResponse>> searchSubServiceByName(String serviceName);

}
