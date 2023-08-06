package fcmb.com.good.services.serviceRender;


import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.servicesRequest.SubServiceRequest2;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.servicesResponse.SubServiceRequestResponse;
import fcmb.com.good.model.dto.response.servicesResponse.SubServiceResponse;
import fcmb.com.good.model.entity.services.SubService;
import fcmb.com.good.model.entity.services.SubServiceRequest;
import fcmb.com.good.model.entity.transaction.Orders;
import fcmb.com.good.model.entity.user.Customer;
import fcmb.com.good.repo.services.SubServiceRepository;
import fcmb.com.good.repo.services.SubServiceRequestRepository;
import fcmb.com.good.repo.transaction.OrdersRepository;
import fcmb.com.good.repo.user.CustomerRepository;
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
public class SubServiceRequestImpl implements SubServiceRequestService {

    private final SubServiceRequestRepository subServiceRequestRepository;
    private final SubServiceRepository subServiceRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final OrdersRepository orderRepository;


    @Override
    /**
     * @Finding the list of all SubServiceRequest
     * @Validate if the List of SubServiceRequest is empty otherwise return record not found
     * @return the list of SubServiceRequest and a Success Message
     * * */
    public ApiResponse<List<SubServiceRequestResponse>> getListOfSubServiceRequest(int page, int size) {

        List<SubServiceRequest> subServiceList = subServiceRequestRepository.findAll(PageRequest.of(page,size)).toList();
        if(subServiceList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(subServiceList, SubServiceRequestResponse.class));

    }

    @Override
    /**
     * @Validate that subService exists, otherwise return subService not found
     * @Validate that order exists, otherwise return order not found
     * Create the SubServiceRequest definition and save
     * @return success message
     * * */
    public ApiResponse<String> addSubServiceRequest(SubServiceRequest2 request) {

        SubService existingSubService  = subServiceRepository.findByUuid(request.getSubServiceId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Orders existingOrder  = orderRepository.findByUuid(request.getOrderId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));


        SubServiceRequest subServiceRequest = new SubServiceRequest();

        subServiceRequest.setServiceName(existingSubService.getServiceName());
        subServiceRequest.setCustomerName(existingOrder.getOrderBy());
        subServiceRequest.setRoomNo(existingOrder.getOrderItemsList().get(0).getRoom());
        subServiceRequest.setNoOfOccupant(request.getNoOfOccupant());
        subServiceRequest.setStatus("pending");

        Integer occupantNumber = null;
        if(request.getNoOfOccupant() > 2 )
            occupantNumber = (request.getNoOfOccupant() - 2);
        subServiceRequest.setPrice(existingSubService.getUnitCost() * occupantNumber);

        subServiceRequest.setSubService(existingSubService);
        subServiceRequest.setOrderNo(existingOrder.getOrderNo());

        subServiceRequestRepository.save(subServiceRequest);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Request made successfully");

    }



    @Override
    /**
     * @Finding the list of all subServiceOptional by uuid
     * @Validate if the List of subServiceOptional is empty otherwise return record not found
     * Create the subServiceOptional definition and get the product Optional by uuid
     * @return the list of subService and a Success Message
     * * */
    public ApiResponse<SubServiceRequestResponse> getSubServiceRequestById(UUID subServiceRequestId) {

        Optional<SubServiceRequest> subServiceOptional = subServiceRequestRepository.findByUuid(subServiceRequestId);
        if(subServiceOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        SubServiceRequest subService = subServiceOptional.get();

        return new ApiResponse<SubServiceRequestResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(subService,SubServiceRequestResponse.class));

    }


    /**
     * @validating subService by uuid
     * @Validate if subService is empty otherwise return record not found
     * @return subService
     * * */
    private SubServiceRequest validateSubServiceRequest(UUID uuid){
        return subServiceRequestRepository.findByUuid(uuid).orElseThrow(()->
                new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));
    }


    @Override
    /**
     * @Validating the list of existingSubService by uuid
     * @Validate if the List of existingSubService is empty otherwise return record not found
     * Create the SubService definition and save
     * @return a Success Message
     * * */
    public ApiResponse<String> updateSubServiceRequest(UUID subServiceRequestId, SubServiceRequest2 request) {

        SubServiceRequest subServiceRequest = validateSubServiceRequest(subServiceRequestId);

        if (request.getNoOfOccupant() != null) {
            subServiceRequest.setNoOfOccupant(request.getNoOfOccupant());
        }

        subServiceRequest.setStatus("In Progress.........");

        subServiceRequestRepository.save(subServiceRequest);
        return new ApiResponse<String>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record updated successfully");

    }

    @Override
    /**
     * @validating subService by uuid
     * @Validate if subService is empty otherwise return record not found
     * @Delete subService
     * @return a Success Message
     * * */
    public ApiResponse<String> deleteSubServiceRequest(UUID subServiceRequestId) {

        SubServiceRequest subServiceRequest = validateSubServiceRequest(subServiceRequestId);
        subServiceRequestRepository.delete(subServiceRequest);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");

    }

    @Override
    public ApiResponse<List<SubServiceResponse>> searchSubServiceByName(String serviceName) {
        return null;
    }
}
