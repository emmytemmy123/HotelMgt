package fcmb.com.good.services.serviceRender;


import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.servicesRequest.SubServiceRequest2;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.servicesResponse.SubServiceRequestResponse;
import fcmb.com.good.model.dto.response.servicesResponse.SubServiceResponse;
import fcmb.com.good.model.entity.activityLog.ActivityLog;
import fcmb.com.good.model.entity.services.ServiceCategory;
import fcmb.com.good.model.entity.services.SubServiceRequest;
import fcmb.com.good.model.entity.transaction.Orders;
import fcmb.com.good.model.entity.transaction.Payment;
import fcmb.com.good.repo.activityLog.ActivityLogRepository;
import fcmb.com.good.repo.services.SubServiceRepository;
import fcmb.com.good.repo.services.SubServiceRequestRepository;
import fcmb.com.good.repo.transaction.OrdersRepository;
import fcmb.com.good.repo.user.UsersRepository;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubServiceRequestImpl implements SubServiceRequestService {

    private final SubServiceRequestRepository subServiceRequestRepository;
    private final SubServiceRepository subServiceRepository;
    private final UsersRepository usersRepository;
    private final OrdersRepository orderRepository;
    private final ActivityLogRepository activityLogRepository;


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

        ServiceCategory existingServiceCategory = subServiceRepository.findByUuid(request.getSubServiceId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Orders existingOrder  = orderRepository.findByUuid(request.getOrderId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        SubServiceRequest subServiceRequest = new SubServiceRequest();

        subServiceRequest.setServiceName(existingServiceCategory.getServiceName());
        subServiceRequest.setCustomerName(existingOrder.getOrderBy());
        subServiceRequest.setRoomNo(existingOrder.getOrderItemsList().get(0).getRoom());
        subServiceRequest.setNoOfOccupant(request.getNoOfOccupant());
        subServiceRequest.setStatus("pending");

        Integer occupantNumber = null;
        if(request.getNoOfOccupant() > 2 )
            occupantNumber = (request.getNoOfOccupant() - 2);
        subServiceRequest.setPrice(existingServiceCategory.getUnitCost() * occupantNumber);

        subServiceRequest.setServiceCategory(existingServiceCategory);
        subServiceRequest.setOrderNo(existingOrder.getOrderNo());
        subServiceRequest.setOrders(existingOrder);

        subServiceRequestRepository.save(subServiceRequest);

        existingOrder.setAmount(existingOrder.getAmount() + subServiceRequest.getPrice());
        existingOrder.setAmountDue(existingOrder.getAmountDue() + subServiceRequest.getPrice());
        existingOrder.setSubServiceRequestList(existingOrder.getSubServiceRequestList());

        orderRepository.save(existingOrder);

        ActivityLog activityLog = new ActivityLog();
        activityLog.setName(existingServiceCategory.getServiceName());
        activityLog.setCategory("add");
        activityLog.setDescription("subServiceRequest log");
        activityLog.setPerformedBy(existingOrder.getOrderBy());
        activityLog.setPerformedDate(LocalDateTime.now());

        activityLogRepository.save(activityLog);

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
     * Create the ServiceCategory definition and save
     * @return a Success Message
     * * */
    public ApiResponse<String> updateSubServiceRequest(UUID subServiceRequestId, SubServiceRequest2 request) {

        SubServiceRequest subServiceRequest = validateSubServiceRequest(subServiceRequestId);

        ServiceCategory serviceCategory = subServiceRequest.getServiceCategory();

        subServiceRequest.setStatus("In Progress.........");

        Orders orders = subServiceRequest.getOrders();

        Payment payment = subServiceRequest.getPayment();

        boolean checkNoOfOccupant = (subServiceRequest.getNoOfOccupant() > request.getNoOfOccupant());
        if (checkNoOfOccupant) {
            subServiceRequest.setNoOfOccupant(subServiceRequest.getNoOfOccupant() - request.getNoOfOccupant());
            subServiceRequest.setPrice(subServiceRequest.getPrice() - (serviceCategory.getUnitCost() * subServiceRequest.getNoOfOccupant()));
            orders.setAmount(orders.getAmount() - (serviceCategory.getUnitCost() * subServiceRequest.getNoOfOccupant()));
            orders.setAmountDue(orders.getAmountDue() - (serviceCategory.getUnitCost() * subServiceRequest.getNoOfOccupant()));
            payment.setTotalAmount(orders.getAmount());
            payment.setBalance(orders.getAmountDue());
        }else{
            subServiceRequest.setNoOfOccupant(request.getNoOfOccupant() - subServiceRequest.getNoOfOccupant());
            subServiceRequest.setPrice(subServiceRequest.getNoOfOccupant() * serviceCategory.getUnitCost()
                    + subServiceRequest.getPrice());
            orders.setAmount(orders.getAmount() + (serviceCategory.getUnitCost() * subServiceRequest.getNoOfOccupant()));
            orders.setAmountDue(orders.getAmountDue() + (serviceCategory.getUnitCost() * subServiceRequest.getNoOfOccupant()));
            payment.setTotalAmount(orders.getAmount());
            payment.setBalance(orders.getAmountDue());
        }

        subServiceRequestRepository.save(subServiceRequest);

        orderRepository.save(orders);

        ActivityLog activityLog = new ActivityLog();
        activityLog.setName("subServiceRequest");
        activityLog.setCategory("update");
        activityLog.setDescription("this is a subServiceRequest log update");
        activityLog.setPerformedBy(subServiceRequest.getCustomerName());
        activityLog.setPerformedDate(LocalDateTime.now());

        activityLogRepository.save(activityLog);

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
