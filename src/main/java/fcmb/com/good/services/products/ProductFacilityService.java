package fcmb.com.good.services.products;


import fcmb.com.good.model.dto.request.productsRequest.ProductFacilityRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.productsResponse.ProductFacilityResponse;

import java.util.List;
import java.util.UUID;

public interface ProductFacilityService {


    ApiResponse<String> addRoomFacility(ProductFacilityRequest request);

    ApiResponse<ProductFacilityResponse>getRoomFacilityById(UUID roomFacilityId);

    ApiResponse<String> updateRoomFacility( UUID roomFacilityId, ProductFacilityRequest request);

    ApiResponse<String> deleteRoomFacility(UUID roomFacilityId);

    ApiResponse<List<ProductFacilityResponse>> getRoomFacilityByRoomNumber(UUID roomUuid);



}
