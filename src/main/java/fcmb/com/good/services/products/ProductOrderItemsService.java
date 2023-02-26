package fcmb.com.good.services.products;

import fcmb.com.good.model.dto.request.productsRequest.ProductOrderItemsRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.productsResponse.ProductOrderItemsResponse;
import fcmb.com.good.model.entity.products.ProductOrderItems;

import java.util.List;
import java.util.UUID;

public interface ProductOrderItemsService {

    ApiResponse<List<ProductOrderItemsResponse>> getListOfProductOrderItems(int page, int size);

    ApiResponse<String> addProductOrderItems(ProductOrderItemsRequest request);

    ApiResponse<ProductOrderItemsResponse> getProductOrderItemsById(UUID productOderItemsId);

    ApiResponse<String> updateProductOrderItems(UUID productOderItemsId, ProductOrderItemsRequest request);

    ApiResponse<String> deleteProductOrderItems(UUID productOderItemsId);


}
