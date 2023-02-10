package fcmb.com.good.services.serviceRender;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.servicesRequest.ServiceRequestRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.servicesResponse.ServiceRequestResponse;
import fcmb.com.good.model.entity.services.ServiceRequest;
import fcmb.com.good.repo.services.ServiceRequestRepository;
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
public class ServiceRequestServiceImpl implements ServiceRequestService {
    private final ServiceRequestRepository serviceRequestRepository;

    @Override
    public ApiResponse<List<ServiceRequestResponse>> getListOfServiceRequest(int page, int size) {
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll(PageRequest.of(page,size)).toList();
        if(serviceRequestList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(serviceRequestList, ServiceRequestResponse.class));
    }

    @Override
    public ApiResponse<String> addServiceRequest(@RequestBody ServiceRequestRequest request) {
        ServiceRequest serviceRequest = Mapper.convertObject(request,ServiceRequest.class);
        serviceRequestRepository.save(serviceRequest);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record added Successfully");
    }

    @Override
    public ApiResponse<ServiceRequestResponse> getServiceRequestById(@RequestParam("id") UUID serviceRequestId) {
        Optional<ServiceRequest> serviceRequest = serviceRequestRepository.findByUuid(serviceRequestId);

        if(serviceRequest.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        ServiceRequest sr = serviceRequest.get();
        return new ApiResponse<ServiceRequestResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(sr,ServiceRequestResponse.class));

    }

    private ServiceRequest validateServiceRequest(UUID uuid){
        Optional<ServiceRequest> serviceRequest = serviceRequestRepository.findByUuid(uuid);
        if(serviceRequest.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return serviceRequest.get();
    }

    @Override
    public ApiResponse<String>  updateServiceRequest(UUID serviceRequestId,
                                                                     @RequestBody ServiceRequestRequest request) {
        ServiceRequest serviceRequest = validateServiceRequest(serviceRequestId);
        serviceRequest.setService_id(request.getService_id());
        serviceRequest.setCustomer_id(request.getCustomer_id());
        serviceRequest.setService_type(request.getService_type());
        serviceRequest.setServiced_by(request.getServiced_by());

        serviceRequestRepository.save(serviceRequest);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Updated Successfully");
    }


    @Override
    public ApiResponse<String>  deleteServiceRequest(UUID serviceRequestId) {
        ServiceRequest serviceRequest = validateServiceRequest(serviceRequestId);
        serviceRequestRepository.delete(serviceRequest);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }


}
