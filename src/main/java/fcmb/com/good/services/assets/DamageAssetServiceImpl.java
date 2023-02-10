package fcmb.com.good.services.assets;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.assetsRequest.DamagedAssetsRequest;
import fcmb.com.good.model.dto.response.assetsResponse.DamagedAssetsResponse;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.entity.assets.DamagedAssets;
import fcmb.com.good.repo.assets.DamagedAssetsRepository;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DamageAssetServiceImpl implements DamagedAssetsService {
    private final DamagedAssetsRepository damagedAssetsRepository;

    @Override
    public ApiResponse<List<DamagedAssetsResponse>> getListOfDamageAssets(int page, int size) {
        List<DamagedAssets> damagedAssetsList = damagedAssetsRepository.findAll(PageRequest.of(page,size)).toList();
        if(damagedAssetsList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(damagedAssetsList, DamagedAssetsResponse.class));
    }

    @Override
    public ApiResponse<String> addDamageAssets(@RequestBody DamagedAssetsRequest request) {
        DamagedAssets damagedAssets = Mapper.convertObject(request,DamagedAssets.class);
        damagedAssets=damagedAssetsRepository.save(damagedAssets);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Added Successfully");
    }

    @Override
    public ApiResponse<DamagedAssetsResponse> getDamageAssetsById(@RequestParam UUID damagedAssetsId) {
        Optional<DamagedAssets> damagedAssets = damagedAssetsRepository.findByUuid(damagedAssetsId);

        if(damagedAssets.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        DamagedAssets da = damagedAssets.get();
        return new ApiResponse<DamagedAssetsResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(), Mapper.convertObject(da, DamagedAssetsResponse.class));

    }

    private DamagedAssets validateDamagedAssets(UUID uuid){
        Optional<DamagedAssets> damagedAssets = damagedAssetsRepository.findByUuid(uuid);
        if(damagedAssets.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return damagedAssets.get();
    }

    @Override
    public ApiResponse<String> updateDamageAssets(UUID damagedAssetsId,
                                                                 @RequestBody DamagedAssetsRequest request) {
        DamagedAssets damagedAssets = validateDamagedAssets(damagedAssetsId);
        damagedAssets.setAsset_id(request.getAsset_id());
        damagedAssets.setAsset_category_id(request.getAsset_category_id());
        damagedAssets.setQuantity(request.getQuantity());
        damagedAssets.setStatus(request.getStatus());
        damagedAssets.setComment(request.getComment());

        damagedAssets = damagedAssetsRepository.save(damagedAssets);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Updated Successfully");
    }

    @Override
    public ApiResponse<String> deleteDamageAssets(UUID damagedAssetsId) {
        DamagedAssets damagedAssets = validateDamagedAssets(damagedAssetsId);
        damagedAssetsRepository.delete(damagedAssets);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }


}
