package fcmb.com.good.services.products;

import fcmb.com.good.model.dto.request.productsRequest.ProductPurchaseRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.productsResponse.ProductPurchaseResponse;
import fcmb.com.good.model.dto.response.productsResponse.ProductResponse;
import fcmb.com.good.model.entity.products.ProductPurchase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ProductPurchaseService {


     ApiResponse<List<ProductPurchaseResponse>> getListOfProductPurchase(int page, int size);

    ApiResponse<String> addProductPurchase(ProductPurchaseRequest request);

    ApiResponse<ProductPurchaseResponse> getProductPurchaseById(UUID productPurchaseId);

    ApiResponse<String> updateProductPurchase( UUID productPurchaseId, ProductPurchaseRequest request);

    ApiResponse<String>deleteProductPurchase(UUID productPurchaseId);

    ApiResponse<List<ProductPurchaseResponse>> searchProductPurchaseByName(String name);

    ApiResponse<List<ProductPurchaseResponse>> findByDateCreatedBetween(LocalDateTime dateCreated, LocalDateTime lastModified);


}
