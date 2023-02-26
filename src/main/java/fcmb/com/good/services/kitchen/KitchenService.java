package fcmb.com.good.services.kitchen;


import fcmb.com.good.model.dto.request.kitchen.KitchenRequest;
import fcmb.com.good.model.dto.request.productsRequest.ProductRequest;
import fcmb.com.good.model.dto.response.kitchen.KitchenResponse;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.productsResponse.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface KitchenService {

    ApiResponse<List<KitchenResponse>> getListOfKitchen(int page, int size);

    ApiResponse<String> addKitchen(KitchenRequest request);

    ApiResponse<KitchenResponse> getKitchenById(UUID kitchenId);

    ApiResponse<String> updateKitchen(UUID kitchenId, KitchenRequest request);

    ApiResponse<String> deleteKitchen(UUID kitchenId);

    ApiResponse<List<KitchenResponse>> searchKitchenByName(String foodName);

    ApiResponse<List<KitchenResponse>> searchKitchenByCategory(String category);



}
