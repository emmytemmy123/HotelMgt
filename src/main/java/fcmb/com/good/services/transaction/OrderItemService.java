package fcmb.com.good.services.transaction;

import fcmb.com.good.dto.ApiResponse;
import fcmb.com.good.model.dto.request.orderItemRequest.OrderItemsRequest;

import java.util.UUID;

public interface OrderItemService {
    ApiResponse addOrderItem(OrderItemsRequest payload);
    ApiResponse updateOrderItem(Long itemId,OrderItemsRequest payload);
    ApiResponse deleteOrderItem(Long itemId);
    ApiResponse findByCustomer(String customerName);
}
