package fcmb.com.good.services.assets;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.assetsRequest.AssetsCategoryRequest;
import fcmb.com.good.model.dto.response.assetsResponse.AssetsCategoryResponse;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.entity.assets.AssetsCategory;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.repo.assets.AssetsCategoryRepository;
import fcmb.com.good.repo.user.UserRepository;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssetsCategoryServiceImpl implements AssetsCategoryService {

    private final AssetsCategoryRepository assetsCategoryRepository;
    private final UserRepository userRepository;

    @Override
    /**
     * @Validate and Find the list of all assetCategory
     * @Validate if the List of assetCategory is empty otherwise return record not found
     * @return the list of assetCategory and a Success Message
     * * */
    public ApiResponse<List<AssetsCategoryResponse>> getListOfAssetsCategory(int page, int size) {
        List<AssetsCategory> assetsCategoryList = assetsCategoryRepository.findAll(PageRequest.of(page,size)).toList();
        if(assetsCategoryList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(assetsCategoryList, AssetsCategoryResponse.class));
    }

    @Override
    /**
     * @Validate that no duplicate assetCategory is allowed
     * @Validate that asset category exists otherwise return record not found
     * Create the asset definition and save
     * @return success message* *
     * * */
    public ApiResponse<String> addAssetsCategory(AssetsCategoryRequest request) {

        Optional<AssetsCategory> assetsCategoryOptional = validateDuplicateAssetsCategory(request.getName());

        AppUser existingUser  = userRepository.findByUuid(request.getCreatedById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        if (!assetsCategoryOptional.isEmpty()) {
            return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
                    "Duplicate Record");
        }

        AssetsCategory assetsCategory = new AssetsCategory();
        assetsCategory.setName(request.getName());
        assetsCategory.setDescription(request.getDescription());
        assetsCategory.setCreatedBy(existingUser);

        assetsCategoryRepository.save(assetsCategory);

        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Added successfully");
    }

//    /**
//     * Set and get the assetsCategory parameters
//     */
//    private AssetsCategory getAssetCategoryFromRequest(AssetsCategoryRequest request) {
//        AssetsCategory assetsCategory = new AssetsCategory();
//        assetsCategory.setName(request.getName());
//        assetsCategory.setType(request.getType());
//        assetsCategory.setDescription(request.getDescription());
//        assetsCategory.setAccount_no(request.getAccount_no());
//
//        return assetsCategory;
//    }

    /**
     * @Validating existingAssetCategoryOptional by name
     * @Validate if the List of existingAssetCategoryOptional is empty otherwise return Duplicate Record
     * */
    private Optional<AssetsCategory> validateDuplicateAssetsCategory(String name) {
        Optional<AssetsCategory> existingAssetCategoryOptional = assetsCategoryRepository.findProductCategoryByName(name);
        return existingAssetCategoryOptional;
    }


    @Override
    /**
     * @Validating and Finding the list of assetCategoryOptional by uuid
     * @Validate if the List of assetCategoryOptional is empty otherwise return record not found
     * Create the assetCategory definition and get the assetCategoryOptional by uuid
     * @return the list of assetCategory and a Success Message
     * * */
    public ApiResponse<AssetsCategoryResponse> getAssetsCategoryById(@PathVariable UUID assetsCategoryId) {
        Optional<AssetsCategory> assetsCategory = assetsCategoryRepository.findByUuid(assetsCategoryId);
        if(assetsCategory.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        AssetsCategory as = assetsCategory.get();
        return new ApiResponse<AssetsCategoryResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(as, AssetsCategoryResponse.class));
    }


    /**
     * @validating assetCategoryOptional by uuid
     * @Validate if the List of assetCategory is empty otherwise return record not found
     * @return assetCategoryOptional
     * * */
    private AssetsCategory validateAssetsCategory(UUID uuid){
        Optional<AssetsCategory> assetsCategory = assetsCategoryRepository.findByUuid(uuid);
        if(assetsCategory.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return assetsCategory.get();
    }

    @Override
    /**
     * @validating assetCategoryOptional by uuid
     * @Validate if the List of assetCategoryOptional is empty otherwise return record not found
     * Create the assetCategory definition and update
     * @return a Success Message
     * * */
    public ApiResponse<String> updateAssetsCategory(UUID assetsCategoryId,AssetsCategoryRequest request) {
        AssetsCategory assetsCategory = validateAssetsCategory(assetsCategoryId);

        assetsCategory.setName(request.getName());
        assetsCategory.setDescription(request.getDescription());

        assetsCategoryRepository.save(assetsCategory);
        return new ApiResponse<String>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record updated successfully");
    }

    @Override
    /**
     * @validate assetCategory by uuid
     * @Validate if assetCategory is empty otherwise return record not found
     * @Delete assetCategory
     * @return a Success Message
     * * */
    public ApiResponse<String> deleteAssetsCategory(UUID assetsCategoryId) {
        AssetsCategory assetsCategory = validateAssetsCategory(assetsCategoryId);
        assetsCategoryRepository.delete(assetsCategory);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }




}

