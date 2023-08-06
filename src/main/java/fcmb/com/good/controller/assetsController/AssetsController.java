package fcmb.com.good.controller.assetsController;



import fcmb.com.good.model.dto.request.assetsRequest.AssetsCategoryRequest;
import fcmb.com.good.model.dto.request.assetsRequest.AssetsRequest;
import fcmb.com.good.model.dto.request.assetsRequest.DamagedAssetsRequest;
import fcmb.com.good.model.dto.response.assetsResponse.AssetsCategoryResponse;
import fcmb.com.good.model.dto.response.assetsResponse.AssetsResponse;
import fcmb.com.good.model.dto.response.assetsResponse.DamagedAssetsResponse;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.services.assets.AssetsCategoryService;
import fcmb.com.good.services.assets.AssetsService;
import fcmb.com.good.services.assets.DamagedAssetsService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import static fcmb.com.good.utills.EndPoints.AssetsEndPoints.*;
import static fcmb.com.good.utills.EndpointParam.*;


@RestController
@RequestMapping(assets)
@RequiredArgsConstructor
public class AssetsController {

    private final AssetsCategoryService assetsCategoryService;
    private final AssetsService assetsService;
    private final DamagedAssetsService damagedAssetsService;


                                        //FIND_LISTS_OF_ASSETS
    @GetMapping(FIND_ASSETS_CATEGORY)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    @ApiOperation(value = "Endpoint for retrieving lists of assetsCategory", response = AssetsCategoryResponse.class, responseContainer = "List")
    public ApiResponse<List<AssetsCategoryResponse>> getListOfAssetsCategory(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                             @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return assetsCategoryService.getListOfAssetsCategory(page,size);
    }

    @GetMapping(FIND_ASSET)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    @ApiOperation(value = "Endpoint for retrieving lists of assets", response = AssetsResponse.class, responseContainer = "List")
    public ApiResponse<List<AssetsResponse>> getListOfAssets(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                             @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return assetsService.getListOfAssets(page,size);
    }

    @GetMapping(FIND_DAMAGED_ASSET)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    @ApiOperation(value = "Endpoint for retrieving lists of damagedAssets", response = DamagedAssetsResponse.class, responseContainer = "List")
    public ApiResponse<List<DamagedAssetsResponse>> getListOfDamageAssets(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                          @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return damagedAssetsService.getListOfDamageAssets(page,size);
    }

                                            //ADD_ASSETS
    @PostMapping(ADD_ASSETS_CATEGORY)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "Endpoint for adding new assetsCategory to database", response = String.class)
    public ApiResponse<String> addAssetsCategory(@Valid @RequestBody AssetsCategoryRequest request) {
        return assetsCategoryService.addAssetsCategory(request);
    }

    @PostMapping(ADD_ASSET)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "Endpoint for adding new assets to database", response = String.class)
    public ApiResponse<String> addEmployee(@Valid @RequestBody AssetsRequest request) {
        return assetsService.addAssets(request);
    }

    @PostMapping(ADD_DAMAGED_ASSET)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "Endpoint for adding new damagedAssets to database", response = String.class)
    public ApiResponse<String> addEmployeeShift(@Valid @RequestBody DamagedAssetsRequest request) {
        return damagedAssetsService.addDamageAssets(request);
    }


                                            //FIND_ASSETS_BY_ID
    @GetMapping(FIND_ASSETS_CATEGORY_BY_ID)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    @ApiOperation(value = "Endpoint for fetching assetCategory by id from database", response = AssetsCategoryResponse.class)
    public ApiResponse<AssetsCategoryResponse> getAssetsCategoryById(@PathVariable(value = "id") UUID assetCategory_id) {
        return assetsCategoryService.getAssetsCategoryById(assetCategory_id);
    }

    @GetMapping(FIND_ASSET_BY_ID)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    @ApiOperation(value = "Endpoint for fetching assets by id from database", response = AssetsResponse.class)
    public ApiResponse<AssetsResponse> getAssetsById(@PathVariable(value = "id") UUID assets_id) {
        return assetsService.getAssetsById(assets_id);
    }

    @GetMapping(FIND_DAMAGED_ASSET_BY_ID)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    @ApiOperation(value = "Endpoint for fetching damagedAssets by id from database", response = DamagedAssetsResponse.class)
    public ApiResponse<DamagedAssetsResponse> getDamageAssetsById(@PathVariable(value = "id") UUID damagedAssets_id) {
        return damagedAssetsService.getDamageAssetsById(damagedAssets_id);
    }


                                            //UPDATE_ASSETS
    @PutMapping(UPDATE_ASSETS_CATEGORY)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "Endpoint for updating assetCategory by id from database", response = String.class)
    public ApiResponse<String> updateAssetsCategory(@PathVariable(value = "id") UUID assetCategory_id,
                                                                    @RequestBody AssetsCategoryRequest request) {
        return assetsCategoryService.updateAssetsCategory(assetCategory_id, request);
    }

    @PutMapping(UPDATE_ASSET)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "Endpoint for updating assets by id from database", response = String.class)
    public ApiResponse<String> updateAssets(@PathVariable(value = "id") UUID assets_id,
                                                    @RequestBody AssetsRequest request) {
        return assetsService.updateAssets(assets_id, request);
    }

    @PutMapping(UPDATE_DAMAGED_ASSET)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "Endpoint for updating damagedAssets by id from database", response = String.class)
    public ApiResponse<String> updateDamageAssets(@PathVariable(value = "id") UUID damagedAssets_id,
                                                                @RequestBody DamagedAssetsRequest request) {
        return damagedAssetsService.updateDamageAssets(damagedAssets_id, request);
    }


                                             //DELETE_ASSETS
    @DeleteMapping(DELETE_ASSETS_CATEGORY)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "Endpoint for deleting assetsCategory by id from database", response = String.class)
    public ApiResponse<String> deleteAssetsCategory(@PathVariable(value = "id") UUID assetsCategory_id) {
        return assetsCategoryService.deleteAssetsCategory(assetsCategory_id);
    }

    @DeleteMapping(DELETE_ASSET)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "Endpoint for deleting assets by id from database", response = String.class)
    public ApiResponse<String> deleteAssets(@PathVariable(value = "id") UUID assets_id) {
        return assetsService.deleteAssets(assets_id);
    }

    @DeleteMapping(DELETE_DAMAGED_ASSET)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "Endpoint for deleting damagedAssets by id from database", response = String.class)
    public ApiResponse<String> deleteEmployeeShift(@PathVariable(value = "id") UUID damagedAssets_id) {
        return damagedAssetsService.deleteDamageAssets(damagedAssets_id);
    }




                        //FIND_DAMAGED_ASSETS_BY_NAME

    @GetMapping(FIND_DAMAGED_ASSETS_BY_NAME)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for retrieving lists of DamagedAssets by name", response = DamagedAssetsResponse.class, responseContainer = "List")
    public ApiResponse<List<DamagedAssetsResponse>> searchListOfDamagedAssetsByName(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                                                     @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size,
                                                                                                     @RequestParam String name ) {
        return damagedAssetsService.searchDamagedAssetByName(name);
    }


}
