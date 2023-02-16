package fcmb.com.good.controller.productsControllers;


import fcmb.com.good.model.dto.request.productsRequest.*;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.productsResponse.*;
import fcmb.com.good.services.products.*;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static fcmb.com.good.utills.EndPoints.ProductEndPoints.*;
import static fcmb.com.good.utills.EndPoints.ProductEndPoints.USERS;
import static fcmb.com.good.utills.EndpointParam.*;
import static fcmb.com.good.utills.EndpointParam.SIZE_DEFAULT;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class ProductController {

    private final ProductOrderItemsService productOrderItemsService;
    private final ProductOrderService productOrderService;
    private final ProductPurchaseService productPurchaseService;
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;




                                            //FIND_LISTS_OF_PRODUCTS
    @GetMapping(FIND_PRODUCT_ORDER_ITEMS)
    @ApiOperation(value = "Endpoint for retrieving lists of productOrderItems", response = ProductOrderItemsResponse.class, responseContainer = "List")
    public ApiResponse<List<ProductOrderItemsResponse>> getListOfProductOrderItems(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                                   @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return productOrderItemsService.getListOfProductOrderItems(page,size);
    }

    @GetMapping(FIND_PRODUCT_ORDER)
    @ApiOperation(value = "Endpoint for retrieving lists of productOrder", response = ProductOrderResponse.class, responseContainer = "List")
    public ApiResponse<List<ProductOrderResponse>> getListOfProductOrder(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                         @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return productOrderService.getListOfProductOrder(page,size);
    }

    @GetMapping(FIND_PRODUCT_PURCHASE)
    @ApiOperation(value = "Endpoint for retrieving lists of productPurchase", response = ProductPurchaseResponse.class, responseContainer = "List")
    public ApiResponse<List<ProductPurchaseResponse>> getListOfProductPurchase(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                             @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return productPurchaseService.getListOfProductPurchase(page,size);
    }

    @GetMapping(FIND_PRODUCT)
    @ApiOperation(value = "Endpoint for retrieving lists of product", response = ProductResponse.class, responseContainer = "List")
    public ApiResponse<List<ProductResponse>> getListOfProduct(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                         @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return productService.getListOfProduct(page,size);
    }

    @GetMapping(FIND_PRODUCT_CATEGORY)
    @ApiOperation(value = "Endpoint for retrieving lists of productCategory", response = ProductCategoryResponse.class, responseContainer = "List")
    public ApiResponse<List<ProductCategoryResponse>> getListOfProductCategory(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                               @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return productCategoryService.getListOfProductCategory(page,size);
    }



                                                //ADD_PRODUCTS
    @PostMapping(ADD_PRODUCT_ORDER_ITEMS)
    @ApiOperation(value = "Endpoint for adding new productOrderItems to database", response = String.class)
    public ApiResponse<String> addProductOrderItems(@Valid @RequestBody ProductOrderItemsRequest request) {
        return productOrderItemsService.addProductOrderItems(request);
    }

    @PostMapping(ADD_PRODUCT_ORDER)
    @ApiOperation(value = "Endpoint for adding new productOder to database", response = String.class)
    public ApiResponse<String> addProductOrder(@Valid @RequestBody ProductOrderRequest request) {
        return productOrderService.addProductOrder(request);
    }

    @PostMapping(ADD_PRODUCT_PURCHASE)
    @ApiOperation(value = "Endpoint for adding new productPurchase to database", response = String.class)
    public ApiResponse<String> addEmployeeShift(@Valid @RequestBody ProductPurchaseRequest request) {
        return productPurchaseService.addProductPurchase(request);
    }

//    @PostMapping(ADD_PRODUCT)
//    @ApiOperation(value = "Endpoint for adding new product to database", response = String.class)
//    public ApiResponse<ProductResponse> addProduct(UUID uuid,@Valid @RequestBody ProductRequest request, boolean isAdd) {
//        return productService.addProducts(request);
//    }
    @PostMapping(ADD_PRODUCT)
    @ApiOperation(value = "Endpoint for adding new product to database", response = String.class)
    public ApiResponse<String> addProduct(@Valid @RequestBody ProductRequest request) {
        return productService.addProducts(request);
    }

    @PostMapping(ADD_PRODUCT_CATEGORY)
    @ApiOperation(value = "Endpoint for adding new productCategory to database", response = String.class)
    public ApiResponse<String> addProductCategory(@Valid @RequestBody ProductCategoryRequest request) {
        return productCategoryService.addProductCategory(request);
    }


                                                         //FIND_PRODUCTS_BY_ID
    @GetMapping(FIND_PRODUCT_ORDER_ITEMS_BY_ID)
    @ApiOperation(value = "Endpoint for fetching productOderItems by id from database", response = ProductOrderItemsResponse.class)
    public ApiResponse<ProductOrderItemsResponse> getProductOrderItemsById(@PathVariable(value = "id") UUID productOrderItems_id) {
        return productOrderItemsService.getProductOrderItemsById(productOrderItems_id);
    }

    @GetMapping(FIND_PRODUCT_ORDER_BY_ID)
    @ApiOperation(value = "Endpoint for fetching productOrder by id from database", response = ProductOrderResponse.class)
    public ApiResponse<ProductOrderResponse> getProductOrderById(@PathVariable(value = "id") UUID productOrder_id) {
        return productOrderService.getProductOrderById(productOrder_id);
    }

    @GetMapping(FIND_PRODUCT_PURCHASE_BY_ID)
    @ApiOperation(value = "Endpoint for fetching productPurchase by id from database", response = ProductPurchaseResponse.class)
    public ApiResponse<ProductPurchaseResponse> getProductPurchaseById(@PathVariable(value = "id") UUID productPurchase_id) {
        return productPurchaseService.getProductPurchaseById(productPurchase_id);
    }

    @GetMapping(FIND_PRODUCT_BY_ID)
    @ApiOperation(value = "Endpoint for fetching products by id from database", response = ProductResponse.class)
    public ApiResponse<ProductResponse> getProductById(@PathVariable(value = "id") UUID product_id) {
        return productService.getProductById(product_id);

    }

    @GetMapping(FIND_PRODUCT_CATEGORY_BY_ID)
    @ApiOperation(value = "Endpoint for fetching productCategory by id from database", response = ProductCategoryResponse.class)
    public ApiResponse<ProductCategoryResponse> getProductCategoryById(@PathVariable(value = "id") UUID productCategory_id) {
        return productCategoryService.getProductCategoryById(productCategory_id);

    }

                                            //FIND_PRODUCTS_BY_CATEGORY

    @GetMapping(SEARCH_PRODUCT_BY_CATEGORY)
    @ApiOperation(value = "Endpoint for searching products by Category", response = ProductResponse.class, responseContainer = "List")
    public ApiResponse<List<ProductResponse>> searchProductsByCategory(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                       @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size,
                                                                       @RequestParam String category ) {
        return productService.searchProductsByProductCategory(category);
    }

                                            //FIND_PRODUCTS_BY_NAME

    @GetMapping(SEARCH_PRODUCT_BY_NAME)
    @ApiOperation(value = "Endpoint for retrieving lists of product by Name", response = ProductResponse.class, responseContainer = "List")
    public ApiResponse<List<ProductResponse>> searchListOfProductByName(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                        @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size,
                                                                        @RequestParam String name ) {
        return productService.searchProductsByName(name);
    }

    @GetMapping(SEARCH_PRODUCT_PURCHASE_BY_NAME)
    @ApiOperation(value = "Endpoint for retrieving lists of productPurchase by Name", response = ProductPurchaseResponse.class, responseContainer = "List")
    public ApiResponse<List<ProductPurchaseResponse>> searchListOfProductPurchaseByName(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                        @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size,
                                                                        @RequestParam String name ) {
        return productPurchaseService.searchProductPurchaseByName(name);
    }

    @GetMapping(SEARCH_PRODUCT_PURCHASE_BY_DATE_RANGE)
    @ApiOperation(value = "Endpoint for retrieving lists of productPurchase by dateRange", response = ProductPurchaseResponse.class, responseContainer = "List")
    public ApiResponse<List<ProductPurchaseResponse>> searchListOfProductPurchaseByDateRange
            (@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
             @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size,
             @RequestParam LocalDateTime dateCreated,
             @RequestParam LocalDateTime lastModified) {
        return productPurchaseService.findByDateCreatedBetween(dateCreated, lastModified);
    }


                                                //UPDATE_PRODUCTS
    @PutMapping(UPDATE_PRODUCT_ORDER_ITEMS)
    @ApiOperation(value = "Endpoint for updating productOrderItems by id from database", response = String.class)
    public ApiResponse<String> updateProductOrderItems(@PathVariable(value = "id") UUID productOrderItem_id,
                                                       @RequestBody ProductOrderItemsRequest request) {
        return productOrderItemsService.updateProductOrderItems(productOrderItem_id, request);
    }

    @PutMapping(UPDATE_PRODUCT_ORDER)
    @ApiOperation(value = "Endpoint for updating productOrder by id from database", response = String.class)
    public ApiResponse<String> updateProductOrder(@PathVariable(value = "id") UUID productOrder_id,
                                                  @RequestBody ProductOrderRequest request) {
        return productOrderService.updateProductOrder(productOrder_id, request);
    }

    @PutMapping(UPDATE_PRODUCT_PURCHASE)
    @ApiOperation(value = "Endpoint for updating productPurchase by id from database", response = String.class)
    public ApiResponse<String> updateProductPurchase(@PathVariable(value = "id") UUID productPurchase_id,
                                                     @RequestBody ProductPurchaseRequest request) {
        return productPurchaseService.updateProductPurchase(productPurchase_id, request);
    }

    @PutMapping(UPDATE_PRODUCT)
    @ApiOperation(value = "Endpoint for updating product by id from database", response = String.class)
    public ApiResponse<String> updateProduct(@PathVariable(value = "id") UUID product_id,
                                                     @RequestBody ProductRequest request) {
        return productService.updateProduct(product_id, request);
    }

    @PutMapping(UPDATE_PRODUCT_CATEGORY)
    @ApiOperation(value = "Endpoint for updating productCategory by id from database", response = String.class)
    public ApiResponse<String> updateProductCategory(@PathVariable(value = "id") UUID productCategory_id,
                                                     @RequestBody ProductCategoryRequest request) {
        return productCategoryService.updateProductCategory(productCategory_id, request);
    }



                                                     //DELETE_USERS
    @DeleteMapping(DELETE_PRODUCT_ORDER_ITEMS)
    @ApiOperation(value = "Endpoint for deleting productOrderItems by id from database", response = String.class)
    public ApiResponse<String> deleteProductOrderItems(@PathVariable(value = "id") UUID productOrderItems_id) {
        return productOrderItemsService.deleteProductOrderItems(productOrderItems_id);
    }

    @DeleteMapping(DELETE_PRODUCT_ORDER)
    @ApiOperation(value = "Endpoint for deleting productOrder by id from database", response = String.class)
    public ApiResponse<String> deleteProductOrder(@PathVariable(value = "id") UUID productOrder_id) {
        return productOrderService.deleteProductOrder(productOrder_id);
    }

    @DeleteMapping(DELETE_PRODUCT_PURCHASE)
    @ApiOperation(value = "Endpoint for deleting productPurchase by id from database", response = String.class)
    public ApiResponse<String> deleteProductPurchase(@PathVariable(value = "id") UUID productPurchase_id) {
        return productPurchaseService.deleteProductPurchase(productPurchase_id);
    }

    @DeleteMapping(DELETE_PRODUCT)
    @ApiOperation(value = "Endpoint for deleting product by id from database", response = String.class)
    public ApiResponse<String> deleteProduct(@PathVariable(value = "id") UUID product_id) {
        return productService.deleteProduct(product_id);
    }

    @DeleteMapping(DELETE_PRODUCT_CATEGORY)
    @ApiOperation(value = "Endpoint for deleting productCategory by id from database", response = String.class)
    public ApiResponse<String> deleteProductCategory(@PathVariable(value = "id") UUID productCategory_id) {
        return productCategoryService.deleteProductCategory(productCategory_id);
    }




}
