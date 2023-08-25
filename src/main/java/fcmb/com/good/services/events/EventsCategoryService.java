package fcmb.com.good.services.events;


import fcmb.com.good.model.dto.request.eventsRequest.EventsCategoryRequest;
import fcmb.com.good.model.dto.response.eventsResponse.EventsCategoryResponse;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface EventsCategoryService {

    ApiResponse<List<EventsCategoryResponse>> getListOfAssetsCategory(int page, int size);

    ApiResponse<String> addAssetsCategory(EventsCategoryRequest payload);

    ApiResponse<EventsCategoryResponse> getAssetsCategoryById(UUID assetsCategoryId);

    ApiResponse<String> updateAssetsCategory(UUID assetsCategoryId, EventsCategoryRequest request);

    ApiResponse<String> deleteAssetsCategory(UUID assetsCategoryId);



}
