package fcmb.com.good.services.activityLog;

import fcmb.com.good.model.dto.response.ApiResponse;
import fcmb.com.good.model.dto.response.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.response.activityLogResponse.ActivityLogResponse;
import fcmb.com.good.model.entity.activityLog.ActivityLog;
import fcmb.com.good.repo.activityLog.ActivityLogRepository;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityLogServiceImpl implements ActivityLogService {

    private final ActivityLogRepository activityLogRepository;




    @Override
    /**
     * @Validate and Find the list of  activityLog
     * @Validate if the List of activityLog is empty otherwise return record not found*
     * @return the list of activityLog and a Success Message
     * * */
    public ApiResponse<List<ActivityLogResponse>> getListOfActivityLog(int page, int size) {

        List<ActivityLog> activityLogList = activityLogRepository.findAll(PageRequest.of(page,size)).toList();

        if(activityLogList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse(AppStatus.SUCCESS.label,
                Mapper.convertList(activityLogList, ActivityLogResponse.class));


    }



    @Override
    /**
     * @validating ActivityLog by uuid
     * @Validate if ActivityLog is empty otherwise return record not found
     * @return List of activityLog by category
     * * */
    public ApiResponse<List<ActivityLogResponse>> getActivityLogByCategory(String category) {

        List<ActivityLog> activityLogList = activityLogRepository.findActivityLogByCategory(category);

        if(activityLogList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse(AppStatus.SUCCESS.label,
                Mapper.convertList(activityLogList, ActivityLogResponse.class));

    }



    @Override
    /**
     * @validating ActivityLog by dateCreated
     * @Validate if ActivityLog is empty otherwise return record not found
     * @return List of activityLog by date
     * * */
    public ApiResponse<List<ActivityLogResponse>> getActivityLogByDateRange(String from, String to) {

        List<ActivityLog> activityLogList = activityLogRepository.findByDateCreatedBetween(from,to);

        if(activityLogList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse(AppStatus.SUCCESS.label,
                Mapper.convertList(activityLogList, ActivityLogResponse.class));

    }

    @Override
    /**
     * @validating ActivityLog by dateRange
     * @Validate if ActivityLog is empty otherwise return record not found
     * @return List of activityLog by dateRange
     * * */
    public ApiResponse<List<ActivityLogResponse>> getActivityLogByDate(String dateCreated) {

        List<ActivityLog> activityLogList = activityLogRepository.findActivityLogByDate(dateCreated);

        if(activityLogList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse(AppStatus.SUCCESS.label,
                Mapper.convertList(activityLogList, ActivityLogResponse.class));


    }


}
