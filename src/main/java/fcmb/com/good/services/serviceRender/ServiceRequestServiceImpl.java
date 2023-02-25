package fcmb.com.good.services.serviceRender;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.servicesRequest.ServiceRequestRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.servicesResponse.ServiceRequestResponse;
import fcmb.com.good.model.entity.rooms.Rooms;
import fcmb.com.good.model.entity.services.ServiceRequest;
import fcmb.com.good.model.entity.services.SubService;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.model.entity.user.Customer;
import fcmb.com.good.model.entity.user.Employee;
import fcmb.com.good.repo.rooms.RoomsRepository;
import fcmb.com.good.repo.services.ServiceRequestRepository;
import fcmb.com.good.repo.services.SubServiceRepository;
import fcmb.com.good.repo.user.CustomerRepository;
import fcmb.com.good.repo.user.EmployeeRepository;
import fcmb.com.good.repo.user.UserRepository;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceRequestServiceImpl implements ServiceRequestService {

    private final ServiceRequestRepository serviceRequestRepository;
    private final SubServiceRepository subServiceRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final RoomsRepository roomsRepository;



    @Override
    /**
     * @Finding the list of all serviceRequest
     * @Validate if the List of serviceRequest is empty otherwise return record not found
     * @return the list of serviceRequest and a Success Message
     * * */
    public ApiResponse<List<ServiceRequestResponse>> getListOfServiceRequest(int page, int size) {
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll(PageRequest.of(page,size)).toList();
        if(serviceRequestList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(serviceRequestList, ServiceRequestResponse.class));
    }



    @Override
    /**
     * @Validate that no duplicate ServiceRequest is allowed
     * @Validate that user creating the ServiceRequest exists, otherwise return user not found
     * Create the ServiceRequest definition and save
     * @return success message
     * * */
    public ApiResponse<String> addServiceRequest(ServiceRequestRequest request) {

        AppUser existingUser  = userRepository.findByUuid(request.getCreatedById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Customer existingCustomer  = customerRepository.findByUuid(request.getCurrentCustomerId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Employee existingEmployee  = employeeRepository.findByUuid(request.getCurrentEmployeeId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Rooms existingRooms  = roomsRepository.findByUuid(request.getCurrentRoomId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        SubService existingSubService  = subServiceRepository.findByUuid(request.getCurrentSubServiceId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        ServiceRequest serviceRequest = new ServiceRequest();

        serviceRequest.setServiceName(request.getServiceName());
        serviceRequest.setServiceBy(existingEmployee.getName());
        serviceRequest.setCreatedBy(existingUser);
        serviceRequest.setServiceRequestNo(request.getServiceRequestNo());
        serviceRequest.setCurrentCustomer(existingCustomer.getName());
        serviceRequest.setServiceCategory(request.getServiceCategory());
        serviceRequest.setPrice(request.getPrice());
        serviceRequest.setPaymentStatus(request.getPaymentStatus());
        serviceRequest.setRooms(existingRooms);
        serviceRequest.setCustomer(existingCustomer);
        serviceRequest.setEmployee(existingEmployee);
        serviceRequest.setCreatedBy(existingUser);
        serviceRequest.setSubService(existingSubService);

        serviceRequestRepository.save(serviceRequest);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record created successfully");

    }

    @Override
    /**
     * @Finding the list of all ServiceRequestOptional by uuid
     * @Validate if the List of ServiceRequestOptional is empty otherwise return record not found
     * Create the ServiceRequestOptional definition and get the product Optional by uuid
     * @return the list of ServiceRequest and a Success Message
     * * */
    public ApiResponse<ServiceRequestResponse> getServiceRequestById(UUID serviceRequestId) {
        Optional<ServiceRequest> serviceRequest = serviceRequestRepository.findByUuid(serviceRequestId);

        if(serviceRequest.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        ServiceRequest sr = serviceRequest.get();

        return new ApiResponse<ServiceRequestResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(sr,ServiceRequestResponse.class));

    }


    /**
     * @validating serviceRequest by uuid
     * @Validate if serviceRequest is empty otherwise return record not found
     * @return serviceRequest
     * * */
    private ServiceRequest validateServiceRequest(UUID uuid){
        Optional<ServiceRequest> serviceRequest = serviceRequestRepository.findByUuid(uuid);
        if(serviceRequest.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return serviceRequest.get();
    }


    @Override
    /**
     * @Validating the list of existingServiceRequest by uuid
     * @Validate if the List of existingServiceRequest is empty otherwise return record not found
     * Create the ServiceRequest definition and save
     * @return a Success Message
     * * */
    public ApiResponse<String>  updateServiceRequest(UUID serviceRequestId, ServiceRequestRequest request) {

        ServiceRequest serviceRequest = validateServiceRequest(serviceRequestId);

        serviceRequest.setServiceName(request.getServiceName());
        serviceRequest.setServiceRequestNo(request.getServiceRequestNo());
        serviceRequest.setServiceCategory(request.getServiceCategory());
        serviceRequest.setPrice(request.getPrice());
        serviceRequest.setPaymentStatus(request.getPaymentStatus());

        serviceRequestRepository.save(serviceRequest);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Updated Successfully");
    }


    @Override
    /**
     * @validating serviceRequest by uuid
     * @Validate if serviceRequest is empty otherwise return record not found
     * @Delete serviceRequest
     * @return a Success Message
     * * */
    public ApiResponse<String>  deleteServiceRequest(UUID serviceRequestId) {
        ServiceRequest serviceRequest = validateServiceRequest(serviceRequestId);
        serviceRequestRepository.delete(serviceRequest);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }

    @Override
    /**
     * @Search the list of all ServiceRequest by name
     * @Validate if the List of ServiceRequest is empty otherwise return record not found*
     * @return the list of ServiceRequest by name
     * * */
    public ApiResponse<List<ServiceRequestResponse>> searchServiceRequestByName(String serviceName) {

        List<ServiceRequest> serviceRequestOptional = serviceRequestRepository.searchServiceRequestByName(serviceName);

        if(serviceRequestOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(serviceRequestOptional, ServiceRequestResponse.class));

    }





}
