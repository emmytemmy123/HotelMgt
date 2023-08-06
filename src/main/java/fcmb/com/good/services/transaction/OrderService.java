package fcmb.com.good.services.transaction;


import fcmb.com.good.model.dto.request.orderItemRequest.OrderItemsRequest;
import fcmb.com.good.model.dto.request.transactionRequest.OrderItemRequest;
import fcmb.com.good.model.dto.request.transactionRequest.OrdersRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.transactionResponse.OrdersResponse;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public interface OrderService {

    ApiResponse<List<OrdersResponse>> getListOfOrder(int page, int size);

    ApiResponse<OrdersResponse> getOrderById(UUID orderId);

    ApiResponse<String> addOrder(OrdersRequest request);

    ApiResponse<List<OrdersResponse>> getOrdersByCustomer(UUID customerUuid);

    ApiResponse<List<OrdersResponse>> findOrderByDate(String dateCreated);

    ApiResponse<String> updateOrder (UUID orderItemUuid, OrderItemRequest request);


}
