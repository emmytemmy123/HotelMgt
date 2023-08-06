package fcmb.com.good.controller.productsControllers;


import fcmb.com.good.model.dto.request.productsRequest.*;
import fcmb.com.good.model.dto.request.transactionRequest.OrdersRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.productsResponse.*;
import fcmb.com.good.repo.products.ProductFacilityRepository;
import fcmb.com.good.services.products.*;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static fcmb.com.good.utills.EndPoints.ProductEndPoints.*;
import static fcmb.com.good.utills.EndpointParam.*;
import static fcmb.com.good.utills.EndpointParam.SIZE_DEFAULT;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(product)
@RequiredArgsConstructor
public class ProductController {

    private final ProductPurchaseService productPurchaseService;
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;
    private final ProductFacilityService productFacilityService;



                                            //FIND_LISTS_OF_PRODUCTS

    @GetMapping(FIND_PRODUCT_PURCHASE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for retrieving lists of productPurchase", response = ProductPurchaseResponse.class, responseContainer = "List")
    public ApiResponse<List<ProductPurchaseResponse>> getListOfProductPurchase(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                             @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return productPurchaseService.getListOfProductPurchase(page,size);
    }

    @GetMapping(FIND_PRODUCT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for retrieving lists of product", response = ProductResponse.class, responseContainer = "List")
    public ApiResponse<List<ProductResponse>> getListOfProduct(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                         @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return productService.getListOfProduct(page,size);
    }

    @GetMapping(FIND_PRODUCT_CATEGORY)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for retrieving lists of productCategory", response = ProductCategoryResponse.class, responseContainer = "List")
    public ApiResponse<List<ProductCategoryResponse>> getListOfProductCategory(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                               @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return productCategoryService.getListOfProductCategory(page,size);
    }



                                                //ADD_PRODUCTS

    @PostMapping(ADD_PRODUCT_PURCHASE)
    @PreAuthorize(" hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for adding new productPurchase to database", response = String.class)
    public ApiResponse<String> addEmployeeShift(@Valid @RequestBody ProductPurchaseRequest request) {
        return productPurchaseService.addProductPurchase(request);
    }

    @PostMapping(ADD_PRODUCT)
    @PreAuthorize(" hasAuthority('ROLE_ADMIN') ")
    @ApiOperation(value = "Endpoint for adding new product to database", response = String.class)
    public ApiResponse<String> addProduct(@Valid @RequestBody ProductRequest request) {
        return productService.addProducts(request);
    }

    @PostMapping(ADD_PRODUCT_CATEGORY)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for adding new productCategory to database", response = String.class)
    public ApiResponse<String> addProductCategory(@Valid @RequestBody ProductCategoryRequest request) {
        return productCategoryService.addProductCategory(request);
    }

    @PostMapping(ADD_PRODUCT_FACILITY)
    @PreAuthorize(" hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for adding new productFacility to database", response = String.class)
    public ApiResponse<String> addProductFacility(@Valid @RequestBody ProductFacilityRequest request) {
        return productFacilityService.addProductFacility(request);
    }


                                                         //FIND_PRODUCTS_BY_ID

    @GetMapping(FIND_PRODUCT_PURCHASE_BY_ID)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for fetching productPurchase by id from database", response = ProductPurchaseResponse.class)
    public ApiResponse<ProductPurchaseResponse> getProductPurchaseById(@PathVariable(value = "id") UUID productPurchase_id) {
        return productPurchaseService.getProductPurchaseById(productPurchase_id);
    }

    @GetMapping(FIND_PRODUCT_BY_ID)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') or hasAuthority('ROLE_USER') ")
    @ApiOperation(value = "Endpoint for fetching products by id from database", response = ProductResponse.class)
    public ApiResponse<ProductResponse> getProductById(@PathVariable(value = "id") UUID product_id) {
        return productService.getProductById(product_id);

    }

    @GetMapping(FIND_PRODUCT_CATEGORY_BY_ID)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') or hasAuthority('ROLE_USER') ")
    @ApiOperation(value = "Endpoint for fetching productCategory by id from database", response = ProductCategoryResponse.class)
    public ApiResponse<ProductCategoryResponse> getProductCategoryById(@PathVariable(value = "id") UUID productCategory_id) {
        return productCategoryService.getProductCategoryById(productCategory_id);

    }

    @GetMapping(FIND_PRODUCT_FACILITY_BY_ID)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') or hasAuthority('ROLE_USER') ")
    @ApiOperation(value = "Endpoint for fetching productFacility by id from database", response = ProductFacilityResponse.class)
    public ApiResponse<ProductFacilityResponse> getProductFacilityById(@PathVariable(value = "id") UUID productFacilityId) {
        return productFacilityService.getProductFacilityById(productFacilityId);

    }



                                            //FIND_PRODUCTS_BY_NAME

    @GetMapping(SEARCH_PRODUCT_BY_NAME)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') or hasAuthority('ROLE_USER') ")
    @ApiOperation(value = "Endpoint for retrieving lists of product by Name", response = ProductResponse.class, responseContainer = "List")
    public ApiResponse<List<ProductResponse>> searchListOfProductByName(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                        @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size,
                                                                        @RequestParam String name ) {
        return productService.searchProductsByName(name);
    }

    @GetMapping(SEARCH_PRODUCT_PURCHASE_BY_NAME)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for retrieving lists of productPurchase by Name", response = ProductPurchaseResponse.class, responseContainer = "List")
    public ApiResponse<List<ProductPurchaseResponse>> searchListOfProductPurchaseByName(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                        @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size,
                                                                        @RequestParam String name ) {
        return productPurchaseService.searchProductPurchaseByName(name);
    }

    @GetMapping(SEARCH_PRODUCT_PURCHASE_BY_DATE_RANGE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for retrieving lists of productPurchase by dateRange", response = ProductPurchaseResponse.class, responseContainer = "List")
    public ApiResponse<List<ProductPurchaseResponse>> searchListOfProductPurchaseByDateRange
            (@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
             @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size,
             @RequestParam LocalDateTime dateCreated,
             @RequestParam LocalDateTime lastModified) {
        return productPurchaseService.findByDateCreatedBetween(dateCreated, lastModified);
    }


                                        //FIND_PRODUCT_FACILITY_BY_PRODUCT_UUID
    @GetMapping(FIND_PRODUCT_FACILITY_BY_PRODUCT_UUID)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') or hasAuthority('ROLE_USER') ")
    @ApiOperation(value = "Endpoint for retrieving lists of productFacility by productId", response = ProductFacilityResponse.class, responseContainer = "List")
    public ApiResponse<List<ProductFacilityResponse>> searchListOfProductFacilityByProductId(@RequestParam UUID uuid ) {
        return productFacilityService.getProductFacilityByProduct(uuid);
    }



                                                //UPDATE_PRODUCTS

    @PutMapping(UPDATE_PRODUCT_PURCHASE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for updating productPurchase by id from database", response = String.class)
    public ApiResponse<String> updateProductPurchase(@PathVariable(value = "id") UUID productPurchase_id,
                                                     @RequestBody ProductPurchaseRequest request) {
        return productPurchaseService.updateProductPurchase(productPurchase_id, request);
    }

    @PutMapping(UPDATE_PRODUCT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    @ApiOperation(value = "Endpoint for updating product by id from database", response = String.class)
    public ApiResponse<String> updateProduct(@PathVariable(value = "id") UUID product_id,
                                                     @RequestBody ProductRequest request) {
        return productService.updateProduct(product_id, request);
    }

    @PutMapping(UPDATE_PRODUCT_CATEGORY)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for updating productCategory by id from database", response = String.class)
    public ApiResponse<String> updateProductCategory(@PathVariable(value = "id") UUID productCategory_id,
                                                     @RequestBody ProductCategoryRequest request) {
        return productCategoryService.updateProductCategory(productCategory_id, request);
    }

    @PutMapping(UPDATE_PRODUCT_FACILITY)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for updating productFacility by id from database", response = String.class)
    public ApiResponse<String> updateProductFacility(@PathVariable(value = "id") UUID productFacilityId,
                                                     @RequestBody ProductFacilityRequest request) {
        return productFacilityService.updateProductFacility(productFacilityId, request);
    }



                                            //DELETE_PRODUCTS


//    @DeleteMapping(DELETE_PRODUCT_PURCHASE)
//    @ApiOperation(value = "Endpoint for deleting productPurchase by id from database", response = String.class)
//    public ApiResponse<String> deleteProductPurchase(@PathVariable(value = "id") UUID productPurchase_id) {
//        return productPurchaseService.deleteProductPurchase(productPurchase_id);
//    }
//
//    @DeleteMapping(DELETE_PRODUCT)
//    @ApiOperation(value = "Endpoint for deleting product by id from database", response = String.class)
//    public ApiResponse<String> deleteProduct(@PathVariable(value = "id") UUID product_id) {
//        return productService.deleteProduct(product_id);
//    }
//
//    @DeleteMapping(DELETE_PRODUCT_CATEGORY)
//    @ApiOperation(value = "Endpoint for deleting productCategory by id from database", response = String.class)
//    public ApiResponse<String> deleteProductCategory(@PathVariable(value = "id") UUID productCategory_id) {
//        return productCategoryService.deleteProductCategory(productCategory_id);
//    }
//
//    @DeleteMapping(DELETE_PRODUCT_FACILITY)
//    @ApiOperation(value = "Endpoint for deleting productFacility by id from database", response = String.class)
//    public ApiResponse<String> deleteProductFacility(@PathVariable(value = "id") UUID productFacilityId) {
//        return productFacilityService.deleteProductFacility(productFacilityId);
//    }



}
