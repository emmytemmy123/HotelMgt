package fcmb.com.good.services.assets;


import fcmb.com.good.model.dto.request.assetsRequest.AssetsCategoryRequest;
import fcmb.com.good.model.dto.response.assetsResponse.AssetsCategoryResponse;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.entity.assets.AssetsCategory;

import java.util.List;
import java.util.UUID;

public interface AssetsCategoryService {

    ApiResponse<List<AssetsCategoryResponse>> getListOfAssetsCategory(int page, int size);

    ApiResponse<String> addAssetsCategory(AssetsCategoryRequest payload);

    ApiResponse<AssetsCategoryResponse> getAssetsCategoryById(UUID assetsCategoryId);

    ApiResponse<String> updateAssetsCategory(UUID assetsCategoryId, AssetsCategoryRequest request);

    ApiResponse<String> deleteAssetsCategory(UUID assetsCategoryId);



}
