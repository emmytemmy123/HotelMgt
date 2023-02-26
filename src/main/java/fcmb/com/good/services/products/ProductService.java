package fcmb.com.good.services.products;

import fcmb.com.good.model.dto.request.productsRequest.ProductRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.productsResponse.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ApiResponse<List<ProductResponse>> getListOfProduct(int page, int size);

    ApiResponse<String> addProducts(ProductRequest request);

    ApiResponse<ProductResponse> getProductById(UUID productId);

    ApiResponse<String> updateProduct(UUID productId, ProductRequest request);

    ApiResponse<String> deleteProduct(UUID productId);

    ApiResponse<List<ProductResponse>> searchProductsByName(String name);

//    ApiResponse<List<ProductResponse>> searchProductsByProductCategory(String productCategory);



}
