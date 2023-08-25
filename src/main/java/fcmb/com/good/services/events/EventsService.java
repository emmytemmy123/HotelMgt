package fcmb.com.good.services.events;

import fcmb.com.good.model.dto.request.eventsRequest.EventsRequest;
import fcmb.com.good.model.dto.response.eventsResponse.EventsResponse;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface EventsService {

    ApiResponse<List<EventsResponse>> getListOfAssets(int page, int size);

    ApiResponse<String> addAssets(EventsRequest request);

    ApiResponse<EventsResponse> getAssetsById(UUID assets_id);

    ApiResponse<String> updateAssets(UUID assets_id, EventsRequest request);

    ApiResponse<String> deleteAssets(UUID assets_id);

}
