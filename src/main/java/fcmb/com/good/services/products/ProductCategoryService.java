package fcmb.com.good.services.products;

import fcmb.com.good.model.dto.request.productsRequest.ProductCategoryRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.productsResponse.ProductCategoryResponse;

import java.util.List;
import java.util.UUID;


public interface ProductCategoryService {

    ApiResponse<List<ProductCategoryResponse>> getListOfProductCategory(int page, int size);

    ApiResponse<String> addProductCategory(ProductCategoryRequest request);

    ApiResponse<ProductCategoryResponse> getProductCategoryById(UUID productCategoryId);

    ApiResponse<String> updateProductCategory( UUID productCategoryId, ProductCategoryRequest request);

    ApiResponse<String> deleteProductCategory(UUID productCategoryId);

}
