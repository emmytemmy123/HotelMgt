package fcmb.com.good.services.serviceRender;

import fcmb.com.good.model.dto.request.servicesRequest.ServiceRequestRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.servicesResponse.ServiceRequestResponse;

import java.util.List;
import java.util.UUID;

public interface ServiceRequestService {

    ApiResponse<List<ServiceRequestResponse>> getListOfServiceRequest(int page, int size);

    ApiResponse<String> addServiceRequest(ServiceRequestRequest request);

    ApiResponse<ServiceRequestResponse> getServiceRequestById(UUID serviceRequestId);

    ApiResponse<String> updateServiceRequest( UUID serviceRequestId, ServiceRequestRequest request);

    ApiResponse<String> deleteServiceRequest(UUID serviceRequestId);

    ApiResponse<List<ServiceRequestResponse>> searchServiceRequestByName(String serviceName);


}
