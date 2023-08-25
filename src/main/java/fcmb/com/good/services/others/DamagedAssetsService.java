package fcmb.com.good.services.others;

import fcmb.com.good.model.dto.request.othersRequest.DamagedAssetsRequest;
import fcmb.com.good.model.dto.response.othersResponse.DamagedAssetsResponse;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface DamagedAssetsService {

    ApiResponse<List<DamagedAssetsResponse>> getListOfDamageAssets(int page, int size);

    ApiResponse<String> addDamageAssets(DamagedAssetsRequest request);

    ApiResponse<DamagedAssetsResponse> getDamageAssetsById(UUID damagedAssetsId);

    ApiResponse<String> updateDamageAssets(UUID damagedAssetsId, DamagedAssetsRequest request);

    ApiResponse<String> deleteDamageAssets(UUID damagedAssetsId);

    ApiResponse<List<DamagedAssetsResponse>> searchDamagedAssetByName(String name);

}
