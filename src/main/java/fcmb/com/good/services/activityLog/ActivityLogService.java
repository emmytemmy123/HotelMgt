package fcmb.com.good.services.activityLog;


import fcmb.com.good.dto.ApiResponse;
import fcmb.com.good.model.dto.response.activityLogResponse.ActivityLogResponse;
import fcmb.com.good.model.dto.response.transactionResponse.PaymentResponse;

import java.util.List;


public interface ActivityLogService {

    ApiResponse<List<ActivityLogResponse>> getListOfActivityLog (int page, int size);

    ApiResponse <List<ActivityLogResponse>> getActivityLogByCategory(String category);

    ApiResponse<List<ActivityLogResponse>> getActivityLogByDateRange(String from, String to);

    ApiResponse<List<ActivityLogResponse>> getActivityLogByDate(String dateCreated);



}
