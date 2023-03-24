package fcmb.com.good.services.assets;


import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.assetsRequest.AssetsRequest;
import fcmb.com.good.model.dto.response.assetsResponse.AssetsResponse;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.entity.assets.Assets;
import fcmb.com.good.model.entity.assets.AssetsCategory;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.repo.assets.AssetsCategoryRepository;
import fcmb.com.good.repo.assets.AssetsRepository;
import fcmb.com.good.repo.user.UserRepository;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssetsServiceImp implements AssetsService {

    private final AssetsRepository assetsRepository;
    private final UserRepository userRepository;
    private final AssetsCategoryRepository assetsCategoryRepository;


    @Override
    /**
     * @Validate and Find the list of all assets
     * @Validate if the List of assets is empty otherwise return record not found
     * @return the list of assets and a Success Message
     * * */
    public ApiResponse<List<AssetsResponse>> getListOfAssets(int page, int size) {
        List<Assets> assetsList = assetsRepository.findAll(PageRequest.of(page,size)).toList();
        if(assetsList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(assetsList, AssetsResponse.class));
    }

    @Override
    /**
     * @Validate that no duplicate assets is allowed
     * @Validate that asset exists otherwise return record not found
     * @Validate that user exists otherwise return record not found
     * @Validate that assetCategory exists otherwise return record not found
     * Create the asset definition and save
     * @return success message* *
     * * */
    public ApiResponse<String> addAssets(AssetsRequest request) {

        Optional<Assets> assetsOptional = validateDuplicateAssets(request.getName());

        AppUser existingUser  = userRepository.findByUuid(request.getCreatedById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        AssetsCategory existingAssetCategory  = assetsCategoryRepository.findByUuid(request.getAssetsCategoryId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));


        if (!assetsOptional.isEmpty()) {
            return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
                    "Duplicate Record");
        }

        Assets assets = new Assets();

        assets.setName(request.getName());
        assets.setDescription(request.getDescription());
        assets.setQuantity(request.getQuantity());
        assets.setStatus("Good Condition");
        assets.setCode(request.getCode());
        assets.setCreatedBy(existingUser);
        assets.setAssetsCategory(existingAssetCategory);

        assetsRepository.save(assets);

        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Added successfully");

    }

    /**
     * @Validating existingAssetOptional by name
     * @Validate if the List of existingAssetOptional is empty otherwise return Duplicate Record
     * */
    private Optional<Assets> validateDuplicateAssets(String name) {
        Optional<Assets> existingAssetOptional = assetsRepository.findAssetByName(name);
        return existingAssetOptional;
    }


    @Override
    /**
     * @Validating and Finding the list of assetsOptional by uuid
     * @Validate if the List of assetsOptional is empty otherwise return record not found
     * Create the asset definition and get the assetsOptional by uuid
     * @return the list of assets and a Success Message
     * * */
    public ApiResponse<AssetsResponse> getAssetsById(@RequestParam UUID assets_id) {
        Optional<Assets> assetsOptional = assetsRepository.findByUuid(assets_id);
        if(assetsOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        Assets assets = assetsOptional.get();
        return new ApiResponse<AssetsResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(assets, AssetsResponse.class));
    }


    /**
     * @validating assetsOptional by uuid
     * @Validate if the List of assetsOptional is empty otherwise return record not found
     * @return assetsOptional
     * * */
    private Assets validateAssets(UUID uuid){
        Optional<Assets> assetsOptional = assetsRepository.findByUuid(uuid);
        if(assetsOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return assetsOptional.get();
    }

    @Override
    /**
     * @validating assetOptional by uuid
     * @Validate if the List of assetOptional is empty otherwise return record not found
     * Create the asset definition and update
     * @return a Success Message
     * * */
    public ApiResponse<String> updateAssets(UUID assetsId,AssetsRequest request) {
        Assets assets = validateAssets(assetsId);

        assets.setName(request.getName());
        assets.setDescription(request.getDescription());
        assets.setQuantity(request.getQuantity()+assets.getQuantity());
        assets.setStatus("good condition");
        assets.setCode(request.getCode());

        assetsRepository.save(assets);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Updated Successfully");
    }

    @Override
    /**
     * @validate asset by uuid
     * @Validate if asset is empty otherwise return record not found
     * @Delete asset
     * @return a Success Message
     * * */
    public ApiResponse<String> deleteAssets(UUID assets_id) {
        Assets assets = validateAssets(assets_id);
        assetsRepository.delete(assets);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }


}
