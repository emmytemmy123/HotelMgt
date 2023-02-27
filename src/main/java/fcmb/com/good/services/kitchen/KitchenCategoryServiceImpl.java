package fcmb.com.good.services.kitchen;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.kitchen.KitchenCategoryRequest;
import fcmb.com.good.model.dto.response.kitchen.KitchenCategoryResponse;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.entity.kitchen.KitchenCategory;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.repo.kitchen.KitchenCategoryRepository;
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
public class KitchenCategoryServiceImpl implements KitchenCategoryService{

    private final KitchenCategoryRepository kitchenCategoryRepository;
    private final UserRepository userRepository;


    @Override
    /**
     * @Validate and Find the list of all kitchenCategory
     * @Validate if the List of kitchenCategory is empty otherwise return record not found*
     * @return the list of kitchenCategory and a Success Message* *
     * * */
    public ApiResponse<List<KitchenCategoryResponse>> getListOfKitchenCategory(int page, int size) {

        List<KitchenCategory> kitchenCategoryList = kitchenCategoryRepository.findAll(PageRequest.of(page,size)).toList();
        if(kitchenCategoryList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(kitchenCategoryList, KitchenCategoryResponse.class));

    }


    /**
     * @Validating existingKitchenCategoryOptional by name
     * @Validate if the List of existingKitchenCategoryOptional is empty otherwise return Duplicate Record
     * */
    private Optional<KitchenCategory> validateDuplicateKitchenCategory(String categoryName) {
        Optional<KitchenCategory> existingKitchenCategoryOptional = kitchenCategoryRepository.findByCategoryName(categoryName);
        return existingKitchenCategoryOptional;

    }



    @Override
    /**
     * @Validate that no duplicate KitchenCategory is allowed
     * @Validate that KitchenCategory exists otherwise return record not found
     * Create the kitchen definition and save
     * @return success message* *
     * * */
    public ApiResponse<String> addKitchenCategory(KitchenCategoryRequest request) {

        Optional<KitchenCategory> kitchenCategoryOptional = validateDuplicateKitchenCategory(request.getCategoryName());

        AppUser existingUser  = userRepository.findByUuid(request.getCreatedById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        if (!kitchenCategoryOptional.isEmpty()) {
            return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
                    "Duplicate Record");
        }

        KitchenCategory kitchenCategory = new KitchenCategory();
        kitchenCategory.setCategoryName(request.getCategoryName());
        kitchenCategory.setDescription(request.getDescription());
        kitchenCategory.setCreatedBy(existingUser);
        kitchenCategoryRepository.save(kitchenCategory);

        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Added successfully");

    }


    @Override
    /**
     * @Validating and Finding the list of kitchenCategoryOptional by uuid
     * @Validate if the List of kitchenCategoryOptional is empty otherwise return record not found
     * Create the kitchenCategory definition and get the kitchenCategoryOptional by uuid
     * @return the list of kitchenCategory and a Success Message
     * * */
    public ApiResponse<KitchenCategoryResponse> getKitchenCategoryById(UUID kitchenCategoryId) {

        Optional<KitchenCategory> kitchenCategoryOptional = kitchenCategoryRepository.findByUuid(kitchenCategoryId);

        if(kitchenCategoryOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        KitchenCategory kitchenCategory = kitchenCategoryOptional.get();

        return new ApiResponse<KitchenCategoryResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(kitchenCategory,KitchenCategoryResponse.class));

    }


    /**
     * @validating kitchenCategoryOptional by uuid
     * @Validate if the List of kitchenCategory is empty otherwise return record not found
     * @return kitchenCategoryOptional
     * * */
    private KitchenCategory validateKitchenCategory(UUID uuid){
        Optional<KitchenCategory> kitchenCategoryOptional = kitchenCategoryRepository.findByUuid(uuid);
        if(kitchenCategoryOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return kitchenCategoryOptional.get();
    }



    @Override
    /**
     * @validating kitchenCategoryOptional by uuid
     * @Validate if the List of kitchenCategoryOptional is empty otherwise return record not found
     * Create the kitchenCategory definition and update
     * @return a Success Message
     * * */
    public ApiResponse<String> updateKitchenCategory(UUID kitchenCategoryId, KitchenCategoryRequest request) {

        KitchenCategory kitchenCategory = validateKitchenCategory(kitchenCategoryId);

        kitchenCategory.setCategoryName(request.getCategoryName());
        kitchenCategory.setDescription(request.getDescription());
        kitchenCategoryRepository.save(kitchenCategory);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Updated Successfully");


    }


    @Override
    /**
     * @validate kitchenCategory by uuid
     * @Validate if kitchenCategory is empty otherwise return record not found
     * @Delete kitchenCategory
     * @return a Success Message
     * * */
    public ApiResponse<String> deleteKitchenCategory(UUID kitchenCategoryId) {

        KitchenCategory kitchenCategory = validateKitchenCategory(kitchenCategoryId);
        kitchenCategoryRepository.delete(kitchenCategory);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");


    }

    @Override
    /**
     * @Search the list of all KitchenCategory by categoryName
     * @Validate if the List of KitchenCategory is empty otherwise return record not found
     * @return the list of KitchenCategory by foodName
     * * */
    public ApiResponse<List<KitchenCategoryResponse>> searchKitchenCategoryByName(String categoryName) {

        List<KitchenCategory> kitchenCategoryResponseList = kitchenCategoryRepository.searchKitchenCategoryByName(categoryName);

        if(kitchenCategoryResponseList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(kitchenCategoryResponseList, KitchenCategoryResponse.class));

    }





}
