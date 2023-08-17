package fcmb.com.good.controller.othersControllers;


import fcmb.com.good.dto.ApiResponse;
import fcmb.com.good.model.dto.response.activityLogResponse.ActivityLogResponse;
import fcmb.com.good.model.dto.response.servicesResponse.SubServiceResponse;
import fcmb.com.good.services.activityLog.ActivityLogService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static fcmb.com.good.utills.EndPoints.ActivityLogEndPoints.*;
import static fcmb.com.good.utills.EndPoints.ServiceEndPoints.FIND_SUB_SERVICE;
import static fcmb.com.good.utills.EndPoints.ServiceEndPoints.service;
import static fcmb.com.good.utills.EndPoints.TransactionEndPoints.*;
import static fcmb.com.good.utills.EndpointParam.*;
import static fcmb.com.good.utills.EndpointParam.SIZE_DEFAULT;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(activityLog)
@RequiredArgsConstructor
public class ActivityLogController {

    private final ActivityLogService activityLogService;



                        //FIND_LIST_OF_ACTIVITY_LOG

    @GetMapping(FIND_LIST_OF_ACTIVITY_LOG)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for retrieving lists of activityLog by category from database", response = ActivityLogResponse.class, responseContainer = "List")
    public ApiResponse<List<ActivityLogResponse>> getListOfActivityLogResponse(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                               @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return activityLogService.getListOfActivityLog(page,size);
    }


                                //FIND_ACTIVITY_LOG_BY_CATEGORY

    @GetMapping(FIND_ACTIVITY_LOG_BY_CATEGORY)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for fetching activityLog by category from database", response = ActivityLogResponse.class, responseContainer = "List")
    public ApiResponse<List<ActivityLogResponse>> getActivityLogByCategory(@RequestParam String category) {
        return activityLogService.getActivityLogByCategory(category);
    }


                                //FIND_ACTIVITY_LOG_BY_DATE_RANGE

    @GetMapping(FIND_LIST_OF_ACTIVITY_LOG_BY_DATE_RANGE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for retrieving lists of activityLog by dateRange", response = ActivityLogResponse.class, responseContainer = "List")
    public fcmb.com.good.dto.ApiResponse<List<ActivityLogResponse>> getListOfActivityLogResponseByDateRange(@RequestParam String from, @RequestParam String to) {
        return activityLogService.getActivityLogByDateRange(from, to);
    }


                                //FIND_ACTIVITY_LOG_BY_DATE

    @GetMapping(FIND_LIST_OF_ACTIVITY_LOG_BY_DATE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for retrieving lists of activityLog by date", response = ActivityLogResponse.class, responseContainer = "List")
    public fcmb.com.good.dto.ApiResponse<List<ActivityLogResponse>> getListOfActivityLogResponseByDate(@RequestParam String performDate) {
        return activityLogService.getActivityLogByDate(performDate);
    }






}
