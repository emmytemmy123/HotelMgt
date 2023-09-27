package fcmb.com.good.services.events;

import fcmb.com.good.model.dto.response.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.eventsRequest.EventsCategoryRequest;
import fcmb.com.good.model.dto.response.eventsResponse.EventsCategoryResponse;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.entity.Events.EventsCategory;
import fcmb.com.good.model.entity.activityLog.ActivityLog;
import fcmb.com.good.model.entity.user.Users;
import fcmb.com.good.repo.activityLog.ActivityLogRepository;
import fcmb.com.good.repo.events.EventsCategoryRepository;
import fcmb.com.good.repo.user.UsersRepository;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventsCategoryServiceImpl implements EventsCategoryService {

    private final EventsCategoryRepository eventsCategoryRepository;
    private final UsersRepository usersRepository;
    private final ActivityLogRepository activityLogRepository;

    @Override
    /**
     * @Validate and Find the list of all assetCategory
     * @Validate if the List of assetCategory is empty otherwise return record not found
     * @return the list of assetCategory and a Success Message
     * * */
    public ApiResponse<List<EventsCategoryResponse>> getListOfAssetsCategory(int page, int size) {
        List<EventsCategory> eventsCategoryList = eventsCategoryRepository.findAll(PageRequest.of(page,size)).toList();

        if(eventsCategoryList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(eventsCategoryList, EventsCategoryResponse.class));
    }

    @Override
    /**
     * @Validate that no duplicate assetCategory is allowed
     * @Validate that asset category exists otherwise return record not found
     * Create the asset definition and save
     * @return success message* *
     * * */
    public ApiResponse<String> addAssetsCategory(EventsCategoryRequest request) {

        Optional<EventsCategory> assetsCategoryOptional = validateDuplicateAssetsCategory(request.getName());

        Users existingUser  = usersRepository.findByUuid(request.getCreatedById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        if (!assetsCategoryOptional.isEmpty()) {
            return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
                    "Duplicate Record");
        }

        EventsCategory eventsCategory = new EventsCategory();
        eventsCategory.setName(request.getName());
        eventsCategory.setDescription(request.getDescription());
        eventsCategory.setCreatedBy(existingUser);

        eventsCategoryRepository.save(eventsCategory);

        ActivityLog activityLog = new ActivityLog();
        activityLog.setName("assetCategory");
        activityLog.setCategory("add");
        activityLog.setDescription("this is a assetCategory add log");
        activityLog.setPerformedBy(existingUser.getName());
        activityLog.setPerformedDate(LocalDateTime.now());

        activityLogRepository.save(activityLog);

        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Added successfully");
    }

//    /**
//     * Set and get the assetsCategory parameters
//     */
//    private EventsCategory getAssetCategoryFromRequest(EventsCategoryRequest request) {
//        EventsCategory assetsCategory = new EventsCategory();
//        assetsCategory.setName(request.getName());
//        assetsCategory.setType(request.getType());
//        assetsCategory.setDescription(request.getDescription());
//        assetsCategory.setAccount_no(request.getAccount_no());
//
//        return assetsCategory;
//    }

    /**
     * @Validating existingAssetCategoryOptional by name
     * @Validate if the List of existingAssetCategoryOptional is empty otherwise return Duplicate Record
     * */
    private Optional<EventsCategory> validateDuplicateAssetsCategory(String name) {
        Optional<EventsCategory> existingAssetCategoryOptional = eventsCategoryRepository.findEventCategoryByName(name);
        return existingAssetCategoryOptional;
    }


    @Override
    /**
     * @Validating and Finding the list of assetCategoryOptional by uuid
     * @Validate if the List of assetCategoryOptional is empty otherwise return record not found
     * Create the assetCategory definition and get the assetCategoryOptional by uuid
     * @return the list of assetCategory and a Success Message
     * * */
    public ApiResponse<EventsCategoryResponse> getAssetsCategoryById(@PathVariable UUID assetsCategoryId) {
        Optional<EventsCategory> assetsCategory = eventsCategoryRepository.findByUuid(assetsCategoryId);
        if(assetsCategory.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        EventsCategory as = assetsCategory.get();
        return new ApiResponse<EventsCategoryResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(as, EventsCategoryResponse.class));
    }


    /**
     * @validating assetCategoryOptional by uuid
     * @Validate if the List of assetCategory is empty otherwise return record not found
     * @return assetCategoryOptional
     * * */
    private EventsCategory validateAssetsCategory(UUID uuid){
        Optional<EventsCategory> assetsCategory = eventsCategoryRepository.findByUuid(uuid);
        if(assetsCategory.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return assetsCategory.get();
    }

    @Override
    /**
     * @validating assetCategoryOptional by uuid
     * @Validate if the List of assetCategoryOptional is empty otherwise return record not found
     * Create the assetCategory definition and update
     * @return a Success Message
     * * */
    public ApiResponse<String> updateAssetsCategory(UUID assetsCategoryId, EventsCategoryRequest request) {
        EventsCategory eventsCategory = validateAssetsCategory(assetsCategoryId);

        eventsCategory.setName(request.getName());
        eventsCategory.setDescription(request.getDescription());

        eventsCategoryRepository.save(eventsCategory);

        ActivityLog activityLog = new ActivityLog();
        activityLog.setName("assetCategory");
        activityLog.setCategory("update");
        activityLog.setDescription("this is a assetCategory update log");
        activityLog.setPerformedBy(String.valueOf(eventsCategory.getCreatedBy()));
        activityLog.setPerformedDate(LocalDateTime.now());

        activityLogRepository.save(activityLog);

        return new ApiResponse<String>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record updated successfully");
    }

    @Override
    /**
     * @validate assetCategory by uuid
     * @Validate if assetCategory is empty otherwise return record not found
     * @Delete assetCategory
     * @return a Success Message
     * * */
    public ApiResponse<String> deleteAssetsCategory(UUID assetsCategoryId) {
        EventsCategory eventsCategory = validateAssetsCategory(assetsCategoryId);
        eventsCategoryRepository.delete(eventsCategory);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }




}

