package fcmb.com.good.services.transaction;


import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.transactionRequest.MaintenanceCategoryRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.transactionResponse.MaintenanceCategoryResponse;
import fcmb.com.good.model.entity.transaction.MaintenanceCategory;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.repo.transaction.MaintenanceCategoryRepository;
import fcmb.com.good.repo.transaction.MaintenanceRepository;
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
public class MaintenanceCategoryServiceImpl implements MaintenanceCategoryService {

    private final MaintenanceCategoryRepository maintenanceCategoryRepository;
    private final MaintenanceRepository maintenanceRepository;
    private final UserRepository userRepository;

    @Override
    /**
     * @Validate and Find the list of MaintenanceCategory
     * @Validate if the List of MaintenanceCategory is empty otherwise return record not found*
     * @return the list of MaintenanceCategory and a Success Message
     * * */
    public ApiResponse<List<MaintenanceCategoryResponse>> getListOfMaintenanceCategory(int page, int size) {

        List<MaintenanceCategory> maintenanceCategoryList = maintenanceCategoryRepository.findAll(PageRequest.of(page,size)).toList();
        if(maintenanceCategoryList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(maintenanceCategoryList, MaintenanceCategoryResponse.class));

    }



    @Override
    /**
     * @Validate that no duplicate maintenanceCategory is allowed
     * @Validate that maintenanceCategory exists otherwise return record not found
     * Create the maintenanceCategory definition and save
     * @return success message
     * * */
    public ApiResponse<String> addMaintenanceCategory(MaintenanceCategoryRequest request) {

        Optional<MaintenanceCategory> maintenanceCategoryOptional = validateDuplicateMaintenanceCategory(request.getName());

        if (!maintenanceCategoryOptional.isEmpty()) {
            return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
                    "Duplicate Record");
        }

        AppUser existingUser  = userRepository.findByUuid(request.getCreatedById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        MaintenanceCategory maintenanceCategory = new MaintenanceCategory();

        maintenanceCategory.setName(request.getName());
        maintenanceCategory.setDescription(request.getDescription());
        maintenanceCategory.setCreatedBy(existingUser);
        maintenanceCategory.setPostedBy(existingUser.getName());

        maintenanceCategoryRepository.save(maintenanceCategory);

        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Added successfully");

    }


    /**
     * @Validating existingExpenseCategoryOption by category
     * @Validate if the List of existingExpenseCategoryOption is empty otherwise return Duplicate Record
     * */
    private Optional<MaintenanceCategory> validateDuplicateMaintenanceCategory(String name) {
        Optional<MaintenanceCategory> maintenanceCategoryOptional = maintenanceCategoryRepository.findByName(name);
        return maintenanceCategoryOptional;
    }


    @Override
    /**
     * @Validating and Finding the list of MaintenanceCategoryOptional by uuid
     * @Validate if the List of MaintenanceCategoryOptional is empty otherwise return record not found
     * Create the MaintenanceCategory definition and get the MaintenanceCategoryOptional by uuid
     * @return the list of MaintenanceCategory and a Success Message
     * * */
    public ApiResponse<MaintenanceCategoryResponse> getMaintenanceCategoryById(UUID maintenanceCategoryId) {

        Optional<MaintenanceCategory> maintenanceCategoryOptional = maintenanceCategoryRepository.findByUuid(maintenanceCategoryId);

        if(maintenanceCategoryOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        MaintenanceCategory maintenanceCategory = maintenanceCategoryOptional.get();

        return new ApiResponse<MaintenanceCategoryResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(maintenanceCategory,MaintenanceCategoryResponse.class));

    }


    /**
     * @validating MaintenanceCategoryOptional by uuid
     * @Validate if the List of MaintenanceCategory is empty otherwise return record not found
     * @return MaintenanceCategoryOptional
     * * */
    private MaintenanceCategory validateMaintenanceCategory(UUID uuid){
        Optional<MaintenanceCategory> maintenanceCategory = maintenanceCategoryRepository.findByUuid(uuid);
        if(maintenanceCategory.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return maintenanceCategory.get();
    }


    @Override
    /**
     * @validating maintenanceCategoryOptional by uuid
     * @Validate if the List of maintenanceCategoryOptional is empty otherwise return record not found
     * Create the maintenanceCategory definition and update
     * @return a Success Message
     * * */
    public ApiResponse<String> updateMaintenanceCategory(UUID maintenanceCategoryId, MaintenanceCategoryRequest request) {

        MaintenanceCategory maintenanceCategory = validateMaintenanceCategory(maintenanceCategoryId);

        maintenanceCategory.setName(request.getName());
        maintenanceCategory.setDescription(request.getDescription());

        maintenanceCategoryRepository.save(maintenanceCategory);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Updated Successfully");

    }



    @Override
    /**
     * @validate expenseCategory by uuid
     * @Validate if expenseCategory is empty otherwise return record not found
     * @Delete expenseCategory
     * @return a Success Message
     * * */
    public ApiResponse<String> deleteMaintenanceCategory(UUID maintenanceCategoryId) {

        MaintenanceCategory maintenanceCategory = validateMaintenanceCategory(maintenanceCategoryId);
        maintenanceCategoryRepository.delete(maintenanceCategory);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");

    }





}
