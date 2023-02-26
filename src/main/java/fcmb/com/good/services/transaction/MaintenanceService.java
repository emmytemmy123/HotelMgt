package fcmb.com.good.services.transaction;

import fcmb.com.good.model.dto.request.transactionRequest.MaintenanceRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.transactionResponse.MaintenanceResponse;

import java.util.List;
import java.util.UUID;

public interface MaintenanceService {

    ApiResponse<List<MaintenanceResponse>> getListOfMaintenance(int page, int size);

    ApiResponse<String> addMaintenance(MaintenanceRequest request);

    ApiResponse<MaintenanceResponse> getMaintenanceById(UUID maintenanceId);

    ApiResponse<String> updateMaintenance(UUID maintenanceId, MaintenanceRequest request);

    ApiResponse<String> deleteMaintenance(UUID maintenanceId);

}
