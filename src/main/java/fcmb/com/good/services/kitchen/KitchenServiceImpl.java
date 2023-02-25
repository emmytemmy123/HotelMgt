package fcmb.com.good.services.kitchen;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.kitchen.KitchenRequest;
import fcmb.com.good.model.dto.response.kitchen.KitchenResponse;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.entity.kitchen.Kitchen;
import fcmb.com.good.model.entity.kitchen.KitchenCategory;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.model.entity.user.Customer;
import fcmb.com.good.repo.kitchen.KitchenCategoryRepository;
import fcmb.com.good.repo.kitchen.KitchenOrderRepository;
import fcmb.com.good.repo.kitchen.KitchenRepository;
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
public class KitchenServiceImpl implements KitchenService{

    private final KitchenRepository kitchenRepository;
    private final KitchenCategoryRepository kitchenCategoryRepository;
    private final KitchenOrderRepository kitchenOrderRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;


    @Override
    /**
     * @Finding the list of all kitchens
     * @Validate if the List of kitchens is empty otherwise return record not found
     * @return the list of kitchens and a Success Message
     * * */
    public ApiResponse<List<KitchenResponse>> getListOfKitchen(int page, int size) {

        List<Kitchen> kitchenList = kitchenRepository.findAll(PageRequest.of(page,size)).toList();
        if(kitchenList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(kitchenList, KitchenResponse.class));

    }


    /**
     * @Validating existingKitchenOptional by foodName
     * @Validate if the List of existingKitchenOptional is empty otherwise return Duplicate Record
     * * */
    private void validateDuplicateKitchen(String foodName){
        Optional<Kitchen> existingKitchenOptional = kitchenRepository.findByFoodName(foodName);
        if(existingKitchenOptional.isPresent())
            throw new RecordNotFoundException("Duplicate record");
    }


    @Override
    /**
     * @Validate that no duplicate kitchenFood is allowed
     * @Validate that user creating the kitchen exists, otherwise return user not found
     * Create the kitchen definition and save
     * @return success message
     * * */
    public ApiResponse<String> addKitchen(KitchenRequest request) {

        validateDuplicateKitchen(request.getFoodName());

        AppUser existingUser  = userRepository.findByUuid(request.getCreatedById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        KitchenCategory existingKitchenCategory  = kitchenCategoryRepository.findByUuid(request.getCurrentKitchenCategoryId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Kitchen kitchen = new Kitchen();
        kitchen.setFoodName(request.getFoodName());
        kitchen.setDescription(request.getDescription());
        kitchen.setQuantity(request.getQuantity());
        kitchen.setPrice(request.getPrice());
        kitchen.setStatus(request.getStatus());
        kitchen.setCategory(request.getCategory());
        kitchen.setPhoto(request.getPhoto());
        kitchen.setCreatedBy(existingUser);
        kitchen.setKitchenCategory(existingKitchenCategory);

        kitchenRepository.save(kitchen);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record created successfully");
    }


    @Override
    /**
     * @Finding the list of all kitchenOptional by uuid
     * @Validate if the List of kitchenOptional is empty otherwise return record not found
     * Create the kitchen definition and get the kitchen Optional by uuid
     * @return the list of kitchen and a Success Message
     * * */
    public ApiResponse<KitchenResponse> getKitchenById(UUID kitchenId) {

        Optional<Kitchen> kitchenOptional = kitchenRepository.findByUuid(kitchenId);

        if(kitchenOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        Kitchen kitchen = kitchenOptional.get();

        return new ApiResponse<KitchenResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(kitchen,KitchenResponse.class));

    }


    /**
     * @validating kitchen by uuid
     * @Validate if kitchen is empty otherwise return record not found
     * @return kitchen
     * * */
    private Kitchen validateKitchen(UUID uuid){
        return kitchenRepository.findByUuid(uuid).orElseThrow(()->
                new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));
    }


    @Override
    /**
     * @validating kitchenOptional by uuid
     * @Validate if the List of kitchenOptional is empty otherwise return record not found
     * Create the kitchen definition and update
     * @return a Success Message
     * * */
    public ApiResponse<String> updateKitchen(UUID kitchenId, KitchenRequest request) {

        Kitchen kitchen = validateKitchen(kitchenId);
        kitchen.setDescription(request.getDescription());
        kitchen.setQuantity(request.getQuantity());
        kitchen.setPrice(request.getPrice());
        kitchen.setStatus(request.getStatus());
        kitchen.setCategory(request.getCategory());
        kitchen.setPhoto(request.getPhoto());

        kitchenRepository.save(kitchen);

        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record updated successfully");
    }


    @Override
    /**
     * @validating kitchen by uuid
     * @Validate if kitchen is empty otherwise return record not found
     * @Delete kitchen
     * @return a Success Message
     * * */
    public ApiResponse<String> deleteKitchen(UUID kitchenId) {
        Kitchen kitchen = validateKitchen(kitchenId);
        kitchenRepository.delete(kitchen);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");

    }


    @Override
    /**
     * @Search the list of all foods by category
     * @Validate if the List of foods is empty otherwise return record not found
     * @return the list of kitchen by foodName
     * * */
    public ApiResponse<List<KitchenResponse>> searchKitchenByName(String foodName) {

    List<Kitchen> kitchenResponseList = kitchenRepository.searchKitchenByName(foodName);

        if(kitchenResponseList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(kitchenResponseList, KitchenResponse.class));


    }


    @Override
    /**
     * @Search the list of all foods by category
     * @Validate if the List of Category is empty otherwise return record not found
     * @return the list of kitchen by categoryName
     * * */
    public ApiResponse<List<KitchenResponse>> searchKitchenByCategory(String category) {
//
        List<Kitchen> searchKitchenByCategory = kitchenRepository.searchKitchenByCategory(category);

        if(searchKitchenByCategory.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(searchKitchenByCategory, KitchenResponse.class));
    }




}
