package fcmb.com.good.services.transaction;


import fcmb.com.good.model.dto.request.transactionRequest.OrderItemRequest;
import fcmb.com.good.model.dto.request.transactionRequest.OrdersRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;

import java.util.UUID;

public interface OrderService {

    ApiResponse<String> addOrder(OrdersRequest request, OrderItemRequest orderItemRequest);

    ApiResponse<String>updateOrder(UUID orderId, OrdersRequest request);

    ApiResponse<String> deleteOrder(UUID orderId);

}
