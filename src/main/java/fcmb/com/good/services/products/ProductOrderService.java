package fcmb.com.good.services.products;

import fcmb.com.good.model.dto.request.productsRequest.ProductOrderRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.productsResponse.ProductOrderResponse;
import fcmb.com.good.model.entity.products.ProductOrder;

import java.util.List;
import java.util.UUID;

public interface ProductOrderService {


    ApiResponse<List<ProductOrderResponse>> getListOfProductOrder(int page, int size);

    ApiResponse<String> addProductOrder(ProductOrderRequest request);

    ApiResponse<ProductOrderResponse> getProductOrderById(UUID productOderId);

    ApiResponse<String> updateProductOrder( UUID productOderId, ProductOrderRequest request);

    ApiResponse<String> deleteProductOrder(UUID productOderId);




}
