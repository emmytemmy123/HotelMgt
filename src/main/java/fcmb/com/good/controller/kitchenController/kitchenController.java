package fcmb.com.good.controller.kitchenController;

import fcmb.com.good.model.dto.request.kitchen.KitchenCategoryRequest;
import fcmb.com.good.model.dto.request.kitchen.KitchenOrderRequest;
import fcmb.com.good.model.dto.request.kitchen.KitchenRequest;
import fcmb.com.good.model.dto.response.kitchen.KitchenCategoryResponse;
import fcmb.com.good.model.dto.response.kitchen.KitchenOrderResponse;
import fcmb.com.good.model.dto.response.kitchen.KitchenResponse;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.repo.kitchen.KitchenOrderRepository;
import fcmb.com.good.services.kitchen.KitchenCategoryService;
import fcmb.com.good.services.kitchen.KitchenOrderService;
import fcmb.com.good.services.kitchen.KitchenService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static fcmb.com.good.utills.EndPoints.KitchenEndPoints.*;
import static fcmb.com.good.utills.EndpointParam.*;

@RestController
@RequestMapping(kitchen)
@RequiredArgsConstructor
public class kitchenController {

        private final KitchenService kitchenService;
        private final KitchenCategoryService kitchenCategoryService;
        private final KitchenOrderService kitchenOrderService;


                                        //FIND_LISTS_OF_KITCHEN
        @GetMapping(FIND_KITCHEN)
        @ApiOperation(value = "Endpoint for retrieving lists of kitchen", response = KitchenResponse.class, responseContainer = "List")
        public ApiResponse<List<KitchenResponse>> getListOfKitchen(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                                       @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
                return kitchenService.getListOfKitchen(page,size);
        }

        @GetMapping(FIND_KITCHEN_CATEGORY)
        @ApiOperation(value = "Endpoint for retrieving lists of kitchenCategory", response = KitchenCategoryResponse.class, responseContainer = "List")
        public ApiResponse<List<KitchenCategoryResponse>> getListOfKitchenCategory(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                   @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
                return kitchenCategoryService.getListOfKitchenCategory(page,size);
        }

        @GetMapping(FIND_KITCHEN_ORDER)
        @ApiOperation(value = "Endpoint for retrieving lists of kitchenOrder", response = KitchenOrderResponse.class, responseContainer = "List")
        public ApiResponse<List<KitchenOrderResponse>> getListOfKitchenOrder(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                                   @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
                return kitchenOrderService.getListOfKitchenOrder(page,size);
        }


                                                //ADD_KITCHEN
        @PostMapping(ADD_KITCHEN)
        @ApiOperation(value = "Endpoint for adding new kitchen to database", response = String.class)
        public ApiResponse<String> addKitchen(@Valid @RequestBody KitchenRequest request) {
                return kitchenService.addKitchen(request);
        }

        @PostMapping(ADD_KITCHEN_CATEGORY)
        @ApiOperation(value = "Endpoint for adding new kitchenCategory to database", response = String.class)
        public ApiResponse<String> addKitchenCategory(@Valid @RequestBody KitchenCategoryRequest request) {
                return kitchenCategoryService.addKitchenCategory(request);
        }

        @PostMapping(ADD_KITCHEN_ORDER)
        @ApiOperation(value = "Endpoint for adding new kitchenOrder to database", response = String.class)
        public ApiResponse<String> addKitchenOrder(@Valid @RequestBody KitchenOrderRequest request) {
                return kitchenOrderService.addKitchenOrder(request);
        }



                                                //FIND_KITCHEN_BY_ID
        @GetMapping(FIND_KITCHEN_BY_ID)
        @ApiOperation(value = "Endpoint for fetching kitchen by id from database", response = KitchenResponse.class)
        public ApiResponse<KitchenResponse> getKitchenById(@PathVariable(value = "id") UUID kitchenId) {
                return kitchenService.getKitchenById(kitchenId);
        }

        @GetMapping(FIND_KITCHEN_CATEGORY_BY_ID)
        @ApiOperation(value = "Endpoint for fetching kitchenCategory by id from database", response = KitchenCategoryResponse.class)
        public ApiResponse<KitchenCategoryResponse> getKitchenCategoryById(@PathVariable(value = "id") UUID kitchenCategoryId) {
                return kitchenCategoryService.getKitchenCategoryById(kitchenCategoryId);
        }

        @GetMapping(FIND_KITCHEN_ORDER_BY_ID)
        @ApiOperation(value = "Endpoint for fetching kitchenOrder by id from database", response = KitchenOrderResponse.class)
        public ApiResponse<KitchenOrderResponse> getKitchenOrderById(@PathVariable(value = "id") UUID kitchenOrderId) {
                return kitchenOrderService.getKitchenOrderById(kitchenOrderId);
        }


                                                //UPDATE_KITCHEN
        @PutMapping(UPDATE_KITCHEN)
        @ApiOperation(value = "Endpoint for updating kitchen by id from database", response = String.class)
        public ApiResponse<String> updateKitchen(@PathVariable(value = "id") UUID kitchenId,
                                                           @RequestBody KitchenRequest request) {
                return kitchenService.updateKitchen(kitchenId, request);
        }

        @PutMapping(UPDATE_KITCHEN_CATEGORY)
        @ApiOperation(value = "Endpoint for updating kitchenCategory by id from database", response = String.class)
        public ApiResponse<String> updateKitchenCategory(@PathVariable(value = "id") UUID kitchenCategoryId,
                                                 @RequestBody KitchenCategoryRequest request) {
                return kitchenCategoryService.updateKitchenCategory(kitchenCategoryId, request);
        }

        @PutMapping(UPDATE_KITCHEN_ORDER)
        @ApiOperation(value = "Endpoint for updating kitchenOrder by id from database", response = String.class)
        public ApiResponse<String> updateKitchenOrder(@PathVariable(value = "id") UUID kitchenOrderId,
                                                         @RequestBody KitchenOrderRequest request) {
                return kitchenOrderService.updateKitchenOrder(kitchenOrderId, request);
        }


                                                //DELETE_KITCHEN
        @DeleteMapping(DELETE_KITCHEN)
        @ApiOperation(value = "Endpoint for deleting kitchen by id from database", response = String.class)
        public ApiResponse<String> deleteKitchen(@PathVariable(value = "id") UUID kitchenId) {
                return kitchenService.deleteKitchen(kitchenId);
        }

        @DeleteMapping(DELETE_KITCHEN_CATEGORY)
        @ApiOperation(value = "Endpoint for deleting kitchenCategory by id from database", response = String.class)
        public ApiResponse<String> deleteKitchenCategory(@PathVariable(value = "id") UUID kitchenCategoryId) {
                return kitchenCategoryService.deleteKitchenCategory(kitchenCategoryId);
        }

        @DeleteMapping(DELETE_KITCHEN_ORDER)
        @ApiOperation(value = "Endpoint for deleting kitchenOrder by id from database", response = String.class)
        public ApiResponse<String> deleteKitchenOrder(@PathVariable(value = "id") UUID kitchenOrderId) {
                return kitchenOrderService.deleteKitchenOrder(kitchenOrderId);
        }




                                                //FIND_KITCHEN_BY_NAME

        @GetMapping(SEARCH_KITCHEN_BY_NAME)
        @ApiOperation(value = "Endpoint for retrieving lists of kitchen by Name", response = KitchenResponse.class, responseContainer = "List")
        public ApiResponse<List<KitchenResponse>> searchListOfKitchenByName(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                            @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size,
                                                                            @RequestParam String foodName ) {
                return kitchenService.searchKitchenByName(foodName);
        }

        @GetMapping(SEARCH_KITCHEN_CATEGORY_BY_NAME)
        @ApiOperation(value = "Endpoint for searching kitchenCategory by name", response = KitchenCategoryResponse.class, responseContainer = "List")
        public ApiResponse<List<KitchenCategoryResponse>> searchKitchenCategoryByName(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                                          @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size,
                                                                                          @RequestParam String categoryName ) {
                return kitchenCategoryService.searchKitchenCategoryByName(categoryName);
        }

        @GetMapping(SEARCH_KITCHEN_ORDER_BY_NAME)
        @ApiOperation(value = "Endpoint for searching kitchenOrder by name", response = KitchenOrderResponse.class, responseContainer = "List")
        public ApiResponse<List<KitchenOrderResponse>> searchKitchenOrderByName(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                                @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size,
                                                                                @RequestParam String foodName ) {
                return kitchenOrderService.searchKitchenOrderByFoodName(foodName);
        }




                                                //FIND_KITCHEN_BY_CATEGORY

        @GetMapping(SEARCH_KITCHEN_BY_CATEGORY)
        @ApiOperation(value = "Endpoint for searching kitchen by Category", response = KitchenResponse.class, responseContainer = "List")
        public ApiResponse<List<KitchenResponse>> searchProductsByCategory(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                           @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size,
                                                                           @RequestParam String category ) {
                return kitchenService.searchKitchenByCategory(category);
        }




}
