package fcmb.com.good.services.transaction;


import fcmb.com.good.model.dto.request.transactionRequest.OrderItemRequest;
import fcmb.com.good.model.dto.request.transactionRequest.OrdersRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.transactionResponse.OrdersResponse;
import fcmb.com.good.model.dto.response.transactionResponse.PaymentResponse;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface OrderService {

    ApiResponse<List<OrdersResponse>> getListOfOrder(int page, int size);

    ApiResponse<OrdersResponse> getOrderById(UUID orderId);

    ApiResponse<String> addOrder(OrdersRequest request);

    ApiResponse<List<OrdersResponse>> findOrderByCustomer(UUID customerId);

    ApiResponse<List<OrdersResponse>> findOrderByDate(Date dateCreated);



}
