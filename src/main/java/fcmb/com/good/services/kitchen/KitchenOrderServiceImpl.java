package fcmb.com.good.services.kitchen;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.kitchen.KitchenOrderRequest;
import fcmb.com.good.model.dto.response.kitchen.KitchenOrderResponse;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.entity.kitchen.Kitchen;
import fcmb.com.good.model.entity.kitchen.KitchenCategory;
import fcmb.com.good.model.entity.kitchen.KitchenOrder;
import fcmb.com.good.model.entity.rooms.Rooms;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.model.entity.user.Customer;
import fcmb.com.good.model.entity.user.Employee;
import fcmb.com.good.repo.kitchen.KitchenCategoryRepository;
import fcmb.com.good.repo.kitchen.KitchenOrderRepository;
import fcmb.com.good.repo.kitchen.KitchenRepository;
import fcmb.com.good.repo.rooms.RoomsRepository;
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
public class KitchenOrderServiceImpl implements KitchenOrderService {

    private final UserRepository userRepository;
    private final KitchenOrderRepository kitchenOrderRepository;
    private final KitchenCategoryRepository kitchenCategoryRepository;
    private final KitchenRepository kitchenRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final RoomsRepository roomsRepository;



    @Override
    /**
     * @Finding the list of all kitchenOrders
     * @Validate if the List of kitchenOrders is empty otherwise return record not found
     * @return the list of kitchenOrders and a Success Message
     * * */
    public ApiResponse<List<KitchenOrderResponse>> getListOfKitchenOrder(int page, int size) {

        List<KitchenOrder> kitchenOrderList = kitchenOrderRepository.findAll(PageRequest.of(page,size)).toList();
        if(kitchenOrderList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(kitchenOrderList, KitchenOrderResponse.class));

    }


    /**
     * @Validating existingKitchenOrderOptional by foodName
     * @Validate if the List of existingKitchenOrderOptional is empty otherwise return Duplicate Record
     * * */
    private void validateDuplicateKitchenOrder(String foodName){
        Optional<KitchenOrder> existingKitchenOrderOptional = kitchenOrderRepository.findByFoodName(foodName);
        if(existingKitchenOrderOptional.isPresent())
            throw new RecordNotFoundException("Duplicate record");
    }


    @Override
    /**
     * @Validate that no duplicate KitchenOrder is allowed
     * @Validate that KitchenOrder exists otherwise return record not found
     * Create the KitchenOrder definition and save
     * @return success message* *
     * * */
    public ApiResponse<String> addKitchenOrder(KitchenOrderRequest request) {

        validateDuplicateKitchenOrder(request.getFoodName());

        AppUser existingUser  = userRepository.findByUuid(request.getCreatedById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        KitchenCategory existingKitchenCategory  = kitchenCategoryRepository.findByUuid(request.getCurrentKitchenCategoryId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Kitchen existingKitchen  = kitchenRepository.findByUuid(request.getCurrentKitchenById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Customer existingCustomer  = customerRepository.findByUuid(request.getCurrentCustomerById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Rooms existingRoom  = roomsRepository.findByUuid(request.getCurrentRoomById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Employee existingEmployee  = employeeRepository.findByUuid(request.getCurrentEmployeeById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));


        KitchenOrder kitchenOrder = new KitchenOrder();

        kitchenOrder.setFoodName(request.getFoodName());
        kitchenOrder.setDescription(request.getDescription());
        kitchenOrder.setRequestNo(request.getRequestNo());
        kitchenOrder.setAccountNo(request.getAccountNo());
        kitchenOrder.setSalesPerson(existingEmployee.getName());
        kitchenOrder.setCurrentCustomer(existingCustomer.getName());
        kitchenOrder.setQuantity(request.getQuantity());
        kitchenOrder.setPrice(existingKitchen.getPrice());
        kitchenOrder.setTotalAmount((existingKitchen.getPrice())*(request.getQuantity()));
        kitchenOrder.setStatus(request.getStatus());
        kitchenOrder.setCategory(request.getCategory());
        kitchenOrder.setCustomer(existingCustomer);
        kitchenOrder.setEmployee(existingEmployee);
        kitchenOrder.setCreatedBy(existingUser);
        kitchenOrder.setRooms(existingRoom);
        kitchenOrder.setKitchenCategory(existingKitchenCategory);
        kitchenOrder.setKitchen(existingKitchen);

        kitchenOrderRepository.save(kitchenOrder);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record created successfully");

    }



    @Override
    /**
     * @Finding the list of all kitchenOrderOptional by uuid
     * @Validate if the List of kitchenOrderOptional is empty otherwise return record not found
     * Create the kitchenOrder definition and get the kitchen Optional by uuid
     * @return the list of kitchenOrder and a Success Message
     * * */
    public ApiResponse<KitchenOrderResponse> getKitchenOrderById(UUID kitchenOderId) {

        Optional<KitchenOrder> kitchenOrderOptional = kitchenOrderRepository.findByUuid(kitchenOderId);

        if(kitchenOrderOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        KitchenOrder kitchenorder = kitchenOrderOptional.get();

        return new ApiResponse<KitchenOrderResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(kitchenorder,KitchenOrderResponse.class));


    }


    /**
     * @validating kitchenOrder by uuid
     * @Validate if kitchenOrder is empty otherwise return record not found
     * @return kitchen
     * * */
    private KitchenOrder validateKitchenOrder(UUID uuid){
        return kitchenOrderRepository.findByUuid(uuid).orElseThrow(()->
                new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));
    }


    @Override
    /**
     * @validating kitchenOrderOptional by uuid
     * @Validate if the List of kitchenOrderOptional is empty otherwise return record not found
     * Create the kitchenOrder definition and update
     * @return a Success Message
     * * */
    public ApiResponse<String> updateKitchenOrder(UUID kitchenOderId, KitchenOrderRequest request) {

        KitchenOrder kitchenOrder = validateKitchenOrder(kitchenOderId);
        kitchenOrder.setFoodName(request.getFoodName());
        kitchenOrder.setDescription(request.getDescription());
        kitchenOrder.setRequestNo(request.getRequestNo());
        kitchenOrder.setAccountNo(request.getAccountNo());
        kitchenOrder.setQuantity(request.getQuantity());
//        kitchenOrder.setPrice(request.getPrice());
        kitchenOrder.setStatus(request.getStatus());
        kitchenOrder.setCategory(request.getCategory());

        kitchenOrderRepository.save(kitchenOrder);

        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record updated successfully");

    }




    @Override
    /**
     * @validating kitchenOrder by uuid
     * @Validate if kitchenOrder is empty otherwise return record not found
     * @Delete kitchenOrder
     * @return a Success Message
     * * */
    public ApiResponse<String> deleteKitchenOrder(UUID kitchenOderId) {

        KitchenOrder kitchenOrder = validateKitchenOrder(kitchenOderId);
        kitchenOrderRepository.delete(kitchenOrder);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");

    }

    @Override
    /**
     * @Search the list of all KitchenOrder by foodName
     * @Validate if the List of KitchenOrder is empty otherwise return record not found
     * @return the list of KitchenOrder by foodName
     * * */
    public ApiResponse<List<KitchenOrderResponse>> searchKitchenOrderByFoodName(String foodName) {

       List<KitchenOrder> kitchenOrderList = kitchenOrderRepository.searchKitchenOrderByFoodNameAndDateCreated(foodName);

        if(kitchenOrderList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(kitchenOrderList,  KitchenOrderResponse.class));

    }


}
