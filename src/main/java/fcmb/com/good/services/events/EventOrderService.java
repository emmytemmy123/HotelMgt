package fcmb.com.good.services.events;


import fcmb.com.good.model.dto.request.eventsRequest.EventOrderRequest;
import fcmb.com.good.model.dto.request.eventsRequest.EventOrderRequest2;
import fcmb.com.good.model.dto.request.eventsRequest.EventsRequest;
import fcmb.com.good.model.dto.response.eventsResponse.EventOrderResponse;
import fcmb.com.good.model.dto.response.eventsResponse.EventsResponse;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface EventOrderService {

    ApiResponse<List<EventOrderResponse>> getListOfEventOrder(int page, int size);

    ApiResponse<String> addEventOrders(EventOrderRequest request);

    ApiResponse<EventOrderResponse> getEventOrderById(UUID eventOrderId);

    ApiResponse<String> updateEventOrder(UUID eventOrderId, EventOrderRequest2 request);




}
