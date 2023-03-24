package fcmb.com.good.services.assets;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.assetsRequest.DamagedAssetsRequest;
import fcmb.com.good.model.dto.response.assetsResponse.DamagedAssetsResponse;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.entity.assets.Assets;
import fcmb.com.good.model.entity.assets.DamagedAssets;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.repo.assets.AssetsCategoryRepository;
import fcmb.com.good.repo.assets.AssetsRepository;
import fcmb.com.good.repo.assets.DamagedAssetsRepository;
import fcmb.com.good.repo.user.UserRepository;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DamageAssetServiceImpl implements DamagedAssetsService {

    private final DamagedAssetsRepository damagedAssetsRepository;
    private final UserRepository userRepository;
    private final AssetsCategoryRepository assetsCategoryRepository;
    private final AssetsRepository assetsRepository;


    @Override
    /**
     * @Validate and Find the list of all damageAssets
     * @Validate if the List of damageAssets is empty otherwise return record not found
     * @return the list of damageAssets and a Success Message
     * * */
    public ApiResponse<List<DamagedAssetsResponse>> getListOfDamageAssets(int page, int size) {
        List<DamagedAssets> damagedAssetsList = damagedAssetsRepository.findAll(PageRequest.of(page,size)).toList();
        if(damagedAssetsList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(damagedAssetsList, DamagedAssetsResponse.class));
    }

    /**
     * @Validating existingDamageAssetOptional by name
     * @Validate if the List of existingDamageAssetOptional is empty otherwise return Duplicate Record
     * */
    private Optional<DamagedAssets> validateDuplicateDamagedAssets(String name) {
        Optional<DamagedAssets> existingDamagedAssetsOptional = damagedAssetsRepository.findDamageAssetByName(name);
        return existingDamagedAssetsOptional;
    }

    @Override
    /**
     * @Validate that no duplicate damageAssets is allowed
     * @Validate that damageAssets exists otherwise return record not found
     * @Validate that user exists otherwise return record not found
     * @Validate that damageAssetsCategory exists otherwise return record not found
     * Create the damageAssets definition and save
     * @return success message
     * * */
    public ApiResponse<String> addDamageAssets(DamagedAssetsRequest request) {

        Optional<DamagedAssets> damagedAssetsOptional = validateDuplicateDamagedAssets(request.getName());

        AppUser existingUser  = userRepository.findByUuid(request.getCreatedById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Assets existingAssets = assetsRepository.findByUuid(request.getAssetId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));


        if (!damagedAssetsOptional.isEmpty()) {
            return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
                    "Duplicate Record");
        }

        DamagedAssets damagedAssets = new DamagedAssets();

        damagedAssets.setName(request.getName());
        damagedAssets.setQuantity(String.valueOf(request.getQuantity()));
        damagedAssets.setStatus("faulty");
        damagedAssets.setComment(request.getComment());
        damagedAssets.setCreatedBy(existingUser);
        damagedAssets.setAssets(existingAssets);

        damagedAssetsRepository.save(damagedAssets);

        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Added successfully");

    }


    @Override
    /**
     * @Validating and Finding the list of damageAssetsOptional by uuid
     * @Validate if the List of damageAssetsOptional is empty otherwise return record not found
     * Create the asset definition and get the damageAssetsOptional by uuid
     * @return the list of assets and a Success Message
     * * */
    public ApiResponse<DamagedAssetsResponse> getDamageAssetsById(UUID damagedAssetsId) {
        Optional<DamagedAssets> damagedAssetsOptional = damagedAssetsRepository.findByUuid(damagedAssetsId);

        if(damagedAssetsOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        DamagedAssets damagedAssets = damagedAssetsOptional.get();

        return new ApiResponse<DamagedAssetsResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(damagedAssets, DamagedAssetsResponse.class));

    }

    /**
     * @validating damageAssetsOptional by uuid
     * @Validate if the List of damageAssetsOptional is empty otherwise return record not found
     * @return damageAssetsOptional
     * * */
    private DamagedAssets validateDamagedAssets(UUID uuid){
        Optional<DamagedAssets> damagedAssetsOptional = damagedAssetsRepository.findByUuid(uuid);
        if(damagedAssetsOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return damagedAssetsOptional.get();
    }


    @Override
    /**
     * @validating damageAssetOptional by uuid
     * @Validate if the List of damageAssetOptional is empty otherwise return record not found
     * Create the damageAsset definition and update
     * @return a Success Message
     * * */
    public ApiResponse<String> updateDamageAssets(UUID damagedAssetsId, DamagedAssetsRequest request) {
        DamagedAssets damagedAssets = validateDamagedAssets(damagedAssetsId);

        damagedAssets.setName(request.getName());
        damagedAssets.setQuantity(String.valueOf(request.getQuantity()));
        damagedAssets.setStatus("faulty");
        damagedAssets.setComment(request.getComment());

        damagedAssetsRepository.save(damagedAssets);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Updated Successfully");
    }

    @Override
    /**
     * @validate damageAsset by uuid
     * @Validate if damageAsset is empty otherwise return record not found
     * @Delete damageAsset
     * @return a Success Message
     * * */
    public ApiResponse<String> deleteDamageAssets(UUID damagedAssetsId) {
        DamagedAssets damagedAssets = validateDamagedAssets(damagedAssetsId);
        damagedAssetsRepository.delete(damagedAssets);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }



    @Override
    /**
     * @Search the list of all damageAssets by name
     * @Validate if the List of damageAssets is empty otherwise return record not found
     * @return the list of damageAssets by name
     * * */
    public ApiResponse<List<DamagedAssetsResponse>> searchDamagedAssetByName(String name) {

        List<DamagedAssets> searchDamageAssetByName = damagedAssetsRepository.searchDamagedAssetsByName(name);

        if(searchDamageAssetByName.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(searchDamageAssetByName, DamagedAssetsResponse.class));
    }


}
