package fcmb.com.good.services.products;


import fcmb.com.good.model.dto.request.productsRequest.ProductFacilityRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.productsResponse.ProductFacilityResponse;

import java.util.List;
import java.util.UUID;

public interface ProductFacilityService {


    ApiResponse<String> addProductFacility(ProductFacilityRequest request);

    ApiResponse<ProductFacilityResponse>getProductFacilityById(UUID productFacilityId);

    ApiResponse<String> updateProductFacility( UUID productFacilityId, ProductFacilityRequest request);

    ApiResponse<String> deleteProductFacility(UUID productFacilityId);

    ApiResponse<List<ProductFacilityResponse>> getProductFacilityByProduct(UUID productFacilityId);



}
