package fcmb.com.good.controller.eventsController;



import fcmb.com.good.model.dto.request.eventsRequest.EventOrderRequest;
import fcmb.com.good.model.dto.request.eventsRequest.EventOrderRequest2;
import fcmb.com.good.model.dto.request.eventsRequest.EventsCategoryRequest;
import fcmb.com.good.model.dto.request.eventsRequest.EventsRequest;
import fcmb.com.good.model.dto.request.othersRequest.DamagedAssetsRequest;
import fcmb.com.good.model.dto.response.eventsResponse.EventOrderResponse;
import fcmb.com.good.model.dto.response.eventsResponse.EventsCategoryResponse;
import fcmb.com.good.model.dto.response.eventsResponse.EventsResponse;
import fcmb.com.good.model.dto.response.othersResponse.DamagedAssetsResponse;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.services.events.EventOrderService;
import fcmb.com.good.services.events.EventsCategoryService;
import fcmb.com.good.services.events.EventsService;
import fcmb.com.good.services.others.DamagedAssetsService;
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
@RequestMapping(events)
@RequiredArgsConstructor
public class EventsController {

    private final EventOrderService eventOrderService;
    private final EventsCategoryService eventsCategoryService;
    private final EventsService eventsService;
    private final DamagedAssetsService damagedAssetsService;


                                        //FIND_LISTS_OF_EVENTS
    @GetMapping(FIND_EVENTS_CATEGORY)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    @ApiOperation(value = "Endpoint for retrieving lists of eventsCategory", response = EventsCategoryResponse.class, responseContainer = "List")
    public ApiResponse<List<EventsCategoryResponse>> getListOfEventsCategory(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                             @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return eventsCategoryService.getListOfAssetsCategory(page,size);
    }

    @GetMapping(FIND_EVENTS)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    @ApiOperation(value = "Endpoint for retrieving lists of Events", response = EventsResponse.class, responseContainer = "List")
    public ApiResponse<List<EventsResponse>> getListOfEvents(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                             @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return eventsService.getListOfAssets(page,size);
    }

    @GetMapping(FIND_EVENT_ORDER)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    @ApiOperation(value = "Endpoint for retrieving lists of EventOrders", response = EventOrderResponse.class, responseContainer = "List")
    public ApiResponse<List<EventOrderResponse>> getListOfEventOrder(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                             @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return eventOrderService.getListOfEventOrder(page,size);
    }

    @GetMapping(FIND_DAMAGED_ASSET)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    @ApiOperation(value = "Endpoint for retrieving lists of damagedAssets", response = DamagedAssetsResponse.class, responseContainer = "List")
    public ApiResponse<List<DamagedAssetsResponse>> getListOfDamageAssets(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                          @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return damagedAssetsService.getListOfDamageAssets(page,size);
    }

                                            //ADD_EVENTS
    @PostMapping(ADD_EVENTS_CATEGORY)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "Endpoint for adding new assetsCategory to database", response = String.class)
    public ApiResponse<String> addEventsCategory(@Valid @RequestBody EventsCategoryRequest request) {
        return eventsCategoryService.addAssetsCategory(request);
    }

    @PostMapping(ADD_EVENTS)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "Endpoint for adding new Events to database", response = String.class)
    public ApiResponse<String> addEvents(@Valid @RequestBody EventsRequest request) {
        return eventsService.addAssets(request);
    }


    @PostMapping(ADD_EVENT_ORDER)
    @PreAuthorize("hasAuthority('ROLE_USER')  or hasAuthority('ROLE_MODERATOR')  or hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "Endpoint for adding new EventOrder to database", response = String.class)
    public ApiResponse<String> addEventOrder(@Valid @RequestBody EventOrderRequest request) {
        return eventOrderService.addEventOrders(request);
    }

    @PostMapping(ADD_DAMAGED_ASSET)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "Endpoint for adding new damagedAssets to database", response = String.class)
    public ApiResponse<String> addDamagedAssets(@Valid @RequestBody DamagedAssetsRequest request) {
        return damagedAssetsService.addDamageAssets(request);
    }


                                            //FIND_EVENTS_BY_ID
    @GetMapping(FIND_EVENTS_CATEGORY_BY_ID)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    @ApiOperation(value = "Endpoint for fetching assetCategory by id from database", response = EventsCategoryResponse.class)
    public ApiResponse<EventsCategoryResponse> getEventsCategoryById(@PathVariable(value = "id") UUID assetCategory_id) {
        return eventsCategoryService.getAssetsCategoryById(assetCategory_id);
    }

    @GetMapping(FIND_EVENTS_BY_ID)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    @ApiOperation(value = "Endpoint for fetching Events by id from database", response = EventsResponse.class)
    public ApiResponse<EventsResponse> getEventsById(@PathVariable(value = "id") UUID assets_id) {
        return eventsService.getAssetsById(assets_id);
    }

    @GetMapping(FIND_DAMAGED_ASSET_BY_ID)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    @ApiOperation(value = "Endpoint for fetching damagedAssets by id from database", response = DamagedAssetsResponse.class)
    public ApiResponse<DamagedAssetsResponse> getDamageAssetsById(@PathVariable(value = "id") UUID damagedAssets_id) {
        return damagedAssetsService.getDamageAssetsById(damagedAssets_id);
    }


                                            //UPDATE_ASSETS
    @PutMapping(UPDATE_EVENTS_CATEGORY)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "Endpoint for updating assetCategory by id from database", response = String.class)
    public ApiResponse<String> updateEventsCategory(@PathVariable(value = "id") UUID assetCategory_id,
                                                                    @RequestBody EventsCategoryRequest request) {
        return eventsCategoryService.updateAssetsCategory(assetCategory_id, request);
    }

    @PutMapping(UPDATE_EVENTS)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "Endpoint for updating Events by id from database", response = String.class)
    public ApiResponse<String> updateEvents(@PathVariable(value = "id") UUID assets_id,
                                                    @RequestBody EventsRequest request) {
        return eventsService.updateAssets(assets_id, request);
    }

    @PutMapping(UPDATE_EVENT_ORDER)
    @PreAuthorize("hasAuthority('ROLE_USER')  or hasAuthority('ROLE_MODERATOR')  or hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "Endpoint for updating eventOrder by id from database", response = String.class)
    public ApiResponse<String> updateEventOrder(@PathVariable(value = "id") UUID eventOrderId,
                                            @RequestBody EventOrderRequest2 request) {
        return eventOrderService.updateEventOrder(eventOrderId, request);
    }


    @PutMapping(UPDATE_DAMAGED_ASSET)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "Endpoint for updating damagedAssets by id from database", response = String.class)
    public ApiResponse<String> updateDamageAssets(@PathVariable(value = "id") UUID damagedAssets_id,
                                                                @RequestBody DamagedAssetsRequest request) {
        return damagedAssetsService.updateDamageAssets(damagedAssets_id, request);
    }


                                             //DELETE_ASSETS
    @DeleteMapping(DELETE_EVENTS_CATEGORY)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "Endpoint for deleting assetsCategory by id from database", response = String.class)
    public ApiResponse<String> deleteEventsCategory(@PathVariable(value = "id") UUID assetsCategory_id) {
        return eventsCategoryService.deleteAssetsCategory(assetsCategory_id);
    }

    @DeleteMapping(DELETE_EVENTS)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "Endpoint for deleting Events by id from database", response = String.class)
    public ApiResponse<String> deleteEvents(@PathVariable(value = "id") UUID assets_id) {
        return eventsService.deleteAssets(assets_id);
    }

    @DeleteMapping(DELETE_DAMAGED_ASSET)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "Endpoint for deleting damagedAssets by id from database", response = String.class)
    public ApiResponse<String> deleteDamageAssets(@PathVariable(value = "id") UUID damagedAssets_id) {
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
