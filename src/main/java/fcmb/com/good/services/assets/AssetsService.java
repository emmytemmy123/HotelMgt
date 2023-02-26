package fcmb.com.good.services.assets;

import fcmb.com.good.model.dto.request.assetsRequest.AssetsRequest;
import fcmb.com.good.model.dto.response.assetsResponse.AssetsResponse;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface AssetsService {

    ApiResponse<List<AssetsResponse>> getListOfAssets(int page, int size);

    ApiResponse<String> addAssets(AssetsRequest request);

    ApiResponse<AssetsResponse> getAssetsById(UUID assets_id);

    ApiResponse<String> updateAssets(UUID assets_id, AssetsRequest request);

    ApiResponse<String> deleteAssets(UUID assets_id);

}
