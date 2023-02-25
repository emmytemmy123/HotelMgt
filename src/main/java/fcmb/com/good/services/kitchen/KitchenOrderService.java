package fcmb.com.good.services.kitchen;


import fcmb.com.good.model.dto.request.kitchen.KitchenOrderRequest;
import fcmb.com.good.model.dto.request.productsRequest.ProductOrderRequest;
import fcmb.com.good.model.dto.response.kitchen.KitchenOrderResponse;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.productsResponse.ProductOrderResponse;

import java.util.List;
import java.util.UUID;

public interface KitchenOrderService {

    ApiResponse<List<KitchenOrderResponse>> getListOfKitchenOrder(int page, int size);

    ApiResponse<String> addKitchenOrder(KitchenOrderRequest request);

    ApiResponse<KitchenOrderResponse> getKitchenOrderById(UUID kitchenOderId);

    ApiResponse<String> updateKitchenOrder( UUID kitchenOderId, KitchenOrderRequest request);

    ApiResponse<String> deleteKitchenOrder(UUID kitchenOderId);

    ApiResponse<List<KitchenOrderResponse>> searchKitchenOrderByFoodName(String foodName);

}
