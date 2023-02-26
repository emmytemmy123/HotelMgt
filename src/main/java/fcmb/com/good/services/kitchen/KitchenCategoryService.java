package fcmb.com.good.services.kitchen;


import fcmb.com.good.model.dto.request.kitchen.KitchenCategoryRequest;
import fcmb.com.good.model.dto.request.productsRequest.ProductCategoryRequest;
import fcmb.com.good.model.dto.response.kitchen.KitchenCategoryResponse;
import fcmb.com.good.model.dto.response.kitchen.KitchenResponse;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.productsResponse.ProductCategoryResponse;

import java.util.List;
import java.util.UUID;

public interface KitchenCategoryService {

    ApiResponse<List<KitchenCategoryResponse>> getListOfKitchenCategory(int page, int size);

    ApiResponse<String> addKitchenCategory(KitchenCategoryRequest request);

    ApiResponse<KitchenCategoryResponse> getKitchenCategoryById(UUID kitchenCategoryId);

    ApiResponse<String> updateKitchenCategory( UUID KitchenCategoryId, KitchenCategoryRequest request);

    ApiResponse<String> deleteKitchenCategory(UUID kitchenCategoryId);

    ApiResponse<List<KitchenCategoryResponse>> searchKitchenCategoryByName(String categoryName);



}
