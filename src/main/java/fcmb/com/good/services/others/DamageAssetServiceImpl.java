package fcmb.com.good.services.others;

import fcmb.com.good.model.dto.response.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.othersRequest.DamagedAssetsRequest;
import fcmb.com.good.model.dto.response.othersResponse.DamagedAssetsResponse;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.entity.activityLog.ActivityLog;
import fcmb.com.good.model.entity.others.DamagedAssets;
import fcmb.com.good.model.entity.user.Users;
import fcmb.com.good.repo.activityLog.ActivityLogRepository;
import fcmb.com.good.repo.events.EventsCategoryRepository;
import fcmb.com.good.repo.events.EventsRepository;
import fcmb.com.good.repo.others.DamagedAssetsRepository;
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
public class DamageAssetServiceImpl implements DamagedAssetsService {

    private final DamagedAssetsRepository damagedAssetsRepository;
    private final UsersRepository usersRepository;
    private final EventsCategoryRepository eventsCategoryRepository;
    private final EventsRepository eventsRepository;
    private final ActivityLogRepository activityLogRepository;


    @Override
    /**
     * @Validate and Find the list of all damageAssets
     * @Validate if the List of damageAssets is empty otherwise return record not found
     * @return the list of damageAssets and a Success Message
     * * */
    public ApiResponse<List<DamagedAssetsResponse>> getListOfDamageAssets(int page, int size) {
        List<DamagedAssets> damagedAssetsList = damagedAssetsRepository.findAll(PageRequest.of(page,size)).toList();
        if(damagedAssetsList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(damagedAssetsList, DamagedAssetsResponse.class));
    }

    /**
     * @Validating existingDamageAssetOptional by name
     * @Validate if the List of existingDamageAssetOptional is empty otherwise return Duplicate Record
     * */
    private Optional<DamagedAssets> validateDuplicateDamagedAssets(String name) {
        Optional<DamagedAssets> existingDamagedAssetsOptional = damagedAssetsRepository.findDamageAssetByName(name);
        return existingDamagedAssetsOptional;
    }

    @Override
    /**
     * @Validate that no duplicate damageAssets is allowed
     * @Validate that damageAssets exists otherwise return record not found
     * @Validate that user exists otherwise return record not found
     * @Validate that damageAssetsCategory exists otherwise return record not found
     * Create the damageAssets definition and save
     * @return success message
     * * */
    public ApiResponse<String> addDamageAssets(DamagedAssetsRequest request) {

        Users existingUser  = usersRepository.findByUuid(request.getCreatedById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        DamagedAssets damagedAssets = new DamagedAssets();

        damagedAssets.setName(request.getName());
        damagedAssets.setEmployeeName(existingUser.getName());
        damagedAssets.setQuantity(request.getQuantity());
        damagedAssets.setPrice(request.getPrice());
        damagedAssets.setStatus("faulty");
        damagedAssets.setLocation(request.getLocation());
        damagedAssets.setCategory(request.getCategory());
        damagedAssets.setComment(request.getComment());
        damagedAssets.setApprovedStatus("pending");
        damagedAssets.setRepairedStatus("pending");
        damagedAssets.setCreatedBy(existingUser);

        damagedAssetsRepository.save(damagedAssets);

        ActivityLog activityLog = new ActivityLog();
        activityLog.setName("damageAsset");
        activityLog.setCategory("add");
        activityLog.setDescription("this is a damageAsset add log");
        activityLog.setPerformedBy(existingUser.getName());
        activityLog.setPerformedDate(LocalDateTime.now());

        activityLogRepository.save(activityLog);

        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Added successfully");

    }


    @Override
    /**
     * @Validating and Finding the list of damageAssetsOptional by uuid
     * @Validate if the List of damageAssetsOptional is empty otherwise return record not found
     * Create the asset definition and get the damageAssetsOptional by uuid
     * @return the list of Events and a Success Message
     * * */
    public ApiResponse<DamagedAssetsResponse> getDamageAssetsById(UUID damagedAssetsId) {
        Optional<DamagedAssets> damagedAssetsOptional = damagedAssetsRepository.findByUuid(damagedAssetsId);

        if(damagedAssetsOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        DamagedAssets damagedAssets = damagedAssetsOptional.get();

        return new ApiResponse<DamagedAssetsResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(damagedAssets, DamagedAssetsResponse.class));

    }

    /**
     * @validating damageAssetsOptional by uuid
     * @Validate if the List of damageAssetsOptional is empty otherwise return record not found
     * @return damageAssetsOptional
     * * */
    private DamagedAssets validateDamagedAssets(UUID uuid){
        Optional<DamagedAssets> damagedAssetsOptional = damagedAssetsRepository.findByUuid(uuid);
        if(damagedAssetsOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return damagedAssetsOptional.get();
    }


    @Override
    /**
     * @validating damageAssetOptional by uuid
     * @Validate if the List of damageAssetOptional is empty otherwise return record not found
     * Create the damageAsset definition and update
     * @return a Success Message
     * * */
    public ApiResponse<String> updateDamageAssets(UUID damagedAssetsId, DamagedAssetsRequest request) {

        DamagedAssets damagedAssets = validateDamagedAssets(damagedAssetsId);

        if(request.getName() !=null){
            damagedAssets.setName(request.getName());
        }
        if(request.getQuantity() !=null){
            damagedAssets.setQuantity(request.getQuantity());
        }
        if(request.getPrice() !=null){
            damagedAssets.setPrice(request.getPrice());
        }
        if(request.getLocation() !=null){
            damagedAssets.setLocation(request.getLocation());
        }
        if(request.getCategory() !=null){
            damagedAssets.setCategory(request.getCategory());
        }
        if(request.getComment() !=null){
            damagedAssets.setComment(request.getComment());
        }
        if(request.getApproveStatus() !=null){
            damagedAssets.setApprovedStatus(request.getApproveStatus());
        }
        if(request.getRepairedStatus() !=null){
            damagedAssets.setRepairedStatus(request.getRepairedStatus());
        }
        if(request.getName() !=null){

        }

        if(damagedAssets.getRepairedStatus().equals ("repaired")){
            damagedAssets.setStatus("repaired completed");
        }

        damagedAssetsRepository.save(damagedAssets);

        ActivityLog activityLog = new ActivityLog();
        activityLog.setName("damageAsset");
        activityLog.setCategory("update");
        activityLog.setDescription("this is a productPurchase add log");
//        activityLog.setPerformedBy(String.valueOf(damagedAssets.getEvents().getCreatedBy()));
        activityLog.setPerformedDate(LocalDateTime.now());

        activityLogRepository.save(activityLog);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Updated Successfully");
    }

    @Override
    /**
     * @validate damageAsset by uuid
     * @Validate if damageAsset is empty otherwise return record not found
     * @Delete damageAsset
     * @return a Success Message
     * * */
    public ApiResponse<String> deleteDamageAssets(UUID damagedAssetsId) {
        DamagedAssets damagedAssets = validateDamagedAssets(damagedAssetsId);
        damagedAssetsRepository.delete(damagedAssets);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }



    @Override
    /**
     * @Search the list of all damageAssets by name
     * @Validate if the List of damageAssets is empty otherwise return record not found
     * @return the list of damageAssets by name
     * * */
    public ApiResponse<List<DamagedAssetsResponse>> searchDamagedAssetByName(String name) {

        List<DamagedAssets> searchDamageAssetByName = damagedAssetsRepository.searchDamagedAssetsByName(name);

        if(searchDamageAssetByName.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(searchDamageAssetByName, DamagedAssetsResponse.class));
    }


}
