package fcmb.com.good.services.transaction;


import fcmb.com.good.model.dto.request.transactionRequest.AccountChartRequest;
import fcmb.com.good.model.dto.request.transactionRequest.OrderItemRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.transactionResponse.AccountChartResponse;

import java.util.List;
import java.util.UUID;

public interface OrderItemsService {

    ApiResponse<String> addOrderItems(OrderItemRequest request);

    ApiResponse<String>updateOrderItems( UUID orderItemsId, OrderItemRequest request);

    ApiResponse<String> deleteOrderItems(UUID orderItemsId);


}
