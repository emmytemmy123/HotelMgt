package fcmb.com.good.services.transaction;


import fcmb.com.good.model.dto.request.transactionRequest.MaintenanceCategoryRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.transactionResponse.MaintenanceCategoryResponse;

import java.util.List;
import java.util.UUID;

public interface MaintenanceCategoryService {

    ApiResponse<List<MaintenanceCategoryResponse>> getListOfMaintenanceCategory(int page, int size);

    ApiResponse<String> addMaintenanceCategory(MaintenanceCategoryRequest request);

    ApiResponse<MaintenanceCategoryResponse> getMaintenanceCategoryById(UUID maintenanceCategoryId);

    ApiResponse<String> updateMaintenanceCategory( UUID maintenanceCategoryId, MaintenanceCategoryRequest request);

    ApiResponse<String> deleteMaintenanceCategory(UUID maintenanceCategoryId);


}
