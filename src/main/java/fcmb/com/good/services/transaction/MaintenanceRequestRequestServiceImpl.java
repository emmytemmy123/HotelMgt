package fcmb.com.good.services.transaction;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.response.transactionResponse.MaintenanceResponse;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.entity.transaction.MaintenanceRequest;
import fcmb.com.good.model.entity.transaction.MaintenanceCategory;
import fcmb.com.good.model.entity.user.Users;
import fcmb.com.good.repo.transaction.MaintenanceCategoryRepository;
import fcmb.com.good.repo.transaction.MaintenanceRequestRepository;
import fcmb.com.good.repo.user.UsersRepository;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MaintenanceRequestRequestServiceImpl implements MaintenanceRequestService {

    private  final MaintenanceRequestRepository maintenanceRequestRepository;
    private final UsersRepository usersRepository;
    private final MaintenanceCategoryRepository maintenanceCategoryRepository;


    @Override
    /**
     * @Validate and Find the list of  MaintenanceRequest
     * @Validate if the List of MaintenanceRequest is empty otherwise return record not found*
     * @return the list of MaintenanceRequest and a Success Message
     * * */
    public ApiResponse<List<MaintenanceResponse>> getListOfMaintenance(int page, int size) {
        List<MaintenanceRequest> maintenanceRequestList = maintenanceRequestRepository.findAll(PageRequest.of(page,size)).toList();

        if(maintenanceRequestList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(maintenanceRequestList, MaintenanceResponse.class));

    }


    /**
     * @Validating existingMaintenanceOptional by name
     * @Validating existingMaintenanceOptional by name
     * @Validate the List of existingMaintenance and existingMaintenanceOptional is present otherwise return Duplicate Record*
     * * */
    private void validateDuplicateMaintenance(String name){

        Optional<MaintenanceRequest> maintenanceOptional = maintenanceRequestRepository.findByName(name);

        if(maintenanceOptional.isPresent())

            throw new RecordNotFoundException("Duplicate record");
    }


    @Override
    /**
     * @Validate that no duplicate MaintenanceRequest is allowed
     * @Validate that MaintenanceRequest exists otherwise return record not found
     * Create the MaintenanceRequest definition and save
     * @return success message
     * * */
    public ApiResponse<String> addMaintenance(fcmb.com.good.model.dto.request.transactionRequest.MaintenanceRequest request) {

//        Users existingUser  = usersRepository.findByUuid(request.getCreatedById())
//                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        MaintenanceCategory existingMaintenanceCategory  = maintenanceCategoryRepository.findByName(request.getCategoryName())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        MaintenanceRequest maintenanceRequest = new MaintenanceRequest();

        maintenanceRequest.setCategory(existingMaintenanceCategory.getName());
        maintenanceRequest.setName(request.getName());
        maintenanceRequest.setDescription(request.getDescription());
        maintenanceRequest.setComment(request.getComment());
        maintenanceRequest.setCost(request.getCost());
        maintenanceRequest.setStatus("pending");
        maintenanceRequest.setLocation(request.getLocation());
        maintenanceRequest.setQuantity(request.getQuantity());
        maintenanceRequest.setAmount((request.getCost())*(request.getQuantity()));
        maintenanceRequest.setMaintainedBy(request.getName());
//        maintenanceRequest.setRequestedBy(existingUser.getName());
        maintenanceRequest.setMaintenanceCategory(existingMaintenanceCategory);
//        maintenanceRequest.setCreatedBy(existingUser);

        maintenanceRequestRepository.save(maintenanceRequest);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record created successfully");

    }



    @Override
    /**
     * @Validating and Finding the list of MaintenanceOptional by uuid
     * @Validate if the List of MaintenanceOptional is empty otherwise return record not found
     * Create the MaintenanceRequest definition and get the MaintenanceOptional by uuid
     * @return the list of MaintenanceOptional and a Success Message
     * * */
    public ApiResponse<MaintenanceResponse> getMaintenanceById(UUID maintenanceId) {
        Optional<MaintenanceRequest> maintenanceOptional = maintenanceRequestRepository.findByUuid(maintenanceId);

        if(maintenanceOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        MaintenanceRequest maintenanceRequest = maintenanceOptional.get();

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(maintenanceRequest,MaintenanceResponse.class));

    }


    /**
     * @validating MaintenanceOptional by uuid
     * @Validate if the List of MaintenanceRequest is empty otherwise return record not found
     * @return MaintenanceOptional
     * * */
    private MaintenanceRequest validateMaintenance(UUID uuid){
        Optional<MaintenanceRequest> maintenanceOptional = maintenanceRequestRepository.findByUuid(uuid);
        if(maintenanceOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return maintenanceOptional.get();
    }



    @Override
    /**
     * @validating MaintenanceOptional by uuid
     * @Validate if the List of MaintenanceOptional is empty otherwise return record not found
     * Create the MaintenanceRequest definition and update
     * @return a Success Message
     * * */
    public ApiResponse<String> updateMaintenance( UUID maintenanceId, fcmb.com.good.model.dto.request.transactionRequest.MaintenanceRequest request) {

        MaintenanceRequest maintenanceRequest = validateMaintenance(maintenanceId);

        maintenanceRequest.setCategory(maintenanceRequest.getCategory());
        maintenanceRequest.setName(request.getName());
        maintenanceRequest.setDescription(request.getDescription());
        maintenanceRequest.setComment(request.getComment());
        maintenanceRequest.setCost(request.getCost());
        maintenanceRequest.setStatus("completed");
        maintenanceRequest.setQuantity(request.getQuantity());
        maintenanceRequest.setAmount((request.getCost())*(request.getQuantity()));

        maintenanceRequest.setDateMaintenance(LocalDateTime.now());

        maintenanceRequestRepository.save(maintenanceRequest);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Updated Successfully");
    }


    @Override
    /**
     * @validate MaintenanceRequest by uuid
     * @Validate if MaintenanceRequest is empty otherwise return record not found
     * @Delete MaintenanceRequest
     * @return a Success Message
     * * */
    public ApiResponse<String> deleteMaintenance(UUID maintenanceId) {
        MaintenanceRequest maintenanceRequest = validateMaintenance(maintenanceId);
        maintenanceRequestRepository.delete(maintenanceRequest);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }


}
