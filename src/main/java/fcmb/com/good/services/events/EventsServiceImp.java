package fcmb.com.good.services.events;


import fcmb.com.good.model.dto.response.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.eventsRequest.EventsRequest;
import fcmb.com.good.model.dto.response.eventsResponse.EventsResponse;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.entity.Events.Events;
import fcmb.com.good.model.entity.Events.EventsCategory;
import fcmb.com.good.model.entity.activityLog.ActivityLog;
import fcmb.com.good.model.entity.user.Users;
import fcmb.com.good.repo.activityLog.ActivityLogRepository;
import fcmb.com.good.repo.events.EventsCategoryRepository;
import fcmb.com.good.repo.events.EventsRepository;
import fcmb.com.good.repo.user.UsersRepository;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventsServiceImp implements EventsService {

    private final EventsRepository eventsRepository;
    private final UsersRepository usersRepository;
    private final EventsCategoryRepository eventsCategoryRepository;
    private final ActivityLogRepository activityLogRepository;


    @Override
    /**
     * @Validate and Find the list of all Events
     * @Validate if the List of Events is empty otherwise return record not found
     * @return the list of Events and a Success Message
     * * */
    public ApiResponse<List<EventsResponse>> getListOfAssets(int page, int size) {
        List<Events> assetsList = eventsRepository.findAll(PageRequest.of(page,size)).toList();
        if(assetsList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(assetsList, EventsResponse.class));
    }

    @Override
    /**
     * @Validate that no duplicate Events is allowed
     * @Validate that asset exists otherwise return record not found
     * @Validate that user exists otherwise return record not found
     * @Validate that assetCategory exists otherwise return record not found
     * Create the asset definition and save
     * @return success message* *
     * * */
    public ApiResponse<String> addAssets(EventsRequest request) {

        Optional<Events> assetsOptional = validateDuplicateAssets(request.getName());

        Users existingUser  = usersRepository.findByUuid(request.getCreatedById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        EventsCategory existingEventCategory  = eventsCategoryRepository.findByUuid(request.getEventsCategoryId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));


        if (!assetsOptional.isEmpty()) {
            return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
                    "Duplicate Record");
        }

        Events events = new Events();

        events.setName(request.getName());
        events.setDescription(request.getDescription());
        events.setCategory(existingEventCategory.getName());
        events.setStatus("available");
        events.setCapacity(request.getCapacity());
        events.setPrice(request.getPrice());
        events.setCreatedBy(existingUser);
        events.setEventsCategory(existingEventCategory);

        eventsRepository.save(events);

        ActivityLog activityLog = new ActivityLog();
        activityLog.setName("asset");
        activityLog.setCategory("add");
        activityLog.setDescription("this is a asset add log");
        activityLog.setPerformedBy(existingUser.getName());
        activityLog.setPerformedDate(LocalDateTime.now());

        activityLogRepository.save(activityLog);

        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Added successfully");

    }

    /**
     * @Validating existingAssetOptional by name
     * @Validate if the List of existingAssetOptional is empty otherwise return Duplicate Record
     * */
    private Optional<Events> validateDuplicateAssets(String name) {
        Optional<Events> existingAssetOptional = eventsRepository.findAssetByName(name);
        return existingAssetOptional;
    }


    @Override
    /**
     * @Validating and Finding the list of assetsOptional by uuid
     * @Validate if the List of assetsOptional is empty otherwise return record not found
     * Create the asset definition and get the assetsOptional by uuid
     * @return the list of Events and a Success Message
     * * */
    public ApiResponse<EventsResponse> getAssetsById(@RequestParam UUID assets_id) {
        Optional<Events> assetsOptional = eventsRepository.findByUuid(assets_id);
        if(assetsOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        Events assets = assetsOptional.get();
        return new ApiResponse<EventsResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(assets, EventsResponse.class));
    }


    /**
     * @validating assetsOptional by uuid
     * @Validate if the List of assetsOptional is empty otherwise return record not found
     * @return assetsOptional
     * * */
    private Events validateAssets(UUID uuid){
        Optional<Events> assetsOptional = eventsRepository.findByUuid(uuid);
        if(assetsOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return assetsOptional.get();
    }

    @Override
    /**
     * @validating assetOptional by uuid
     * @Validate if the List of assetOptional is empty otherwise return record not found
     * Create the asset definition and update
     * @return a Success Message
     * * */
    public ApiResponse<String> updateAssets(UUID assetsId, EventsRequest request) {
        Events events = validateAssets(assetsId);

        events.setName(request.getName());
        events.setDescription(request.getDescription());
        events.setCategory(request.getCategory());
        events.setStatus("Good Condition");
        events.setCapacity(request.getCapacity());
        events.setPrice(request.getPrice());

        eventsRepository.save(events);

        ActivityLog activityLog = new ActivityLog();
        activityLog.setName("event");
        activityLog.setCategory("add");
        activityLog.setDescription("this is a asset add log");
        activityLog.setPerformedBy(String.valueOf(events.getCreatedBy()));
        activityLog.setPerformedDate(LocalDateTime.now());

        activityLogRepository.save(activityLog);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Updated Successfully");
    }

    @Override
    /**
     * @validate asset by uuid
     * @Validate if asset is empty otherwise return record not found
     * @Delete asset
     * @return a Success Message
     * * */
    public ApiResponse<String> deleteAssets(UUID assets_id) {
        Events assets = validateAssets(assets_id);
        eventsRepository.delete(assets);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }


}
