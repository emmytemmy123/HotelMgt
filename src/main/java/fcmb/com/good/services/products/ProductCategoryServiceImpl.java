package fcmb.com.good.services.products;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.filter.JwtFilter;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.productsRequest.ProductCategoryRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.productsResponse.ProductCategoryResponse;
import fcmb.com.good.model.entity.products.ProductType;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.repo.products.ProductCategoryRepository;
import fcmb.com.good.repo.user.UserRepository;
import fcmb.com.good.utills.EmailUtils;
import fcmb.com.good.utills.JwtUtil;
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
public class ProductCategoryServiceImpl implements ProductCategoryService{

    private  final ProductCategoryRepository productCategoryRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final JwtFilter jwtFilter;
    private final EmailUtils emailUtils;

    @Override
    /**
     * @Validate and Find the list of all productCategory
     * @Validate if the List of productCategory is empty otherwise return record not found*
     * @return the list of productCategory and a Success Message* *
     * * */
    public ApiResponse<List<ProductCategoryResponse>> getListOfProductCategory(int page, int size) {
//        if(jwtFilter.isAdmin() || jwtFilter.isEmployee()) {

            List<ProductType> productCategoryList = productCategoryRepository.findAll(PageRequest.of(page,size)).toList();
            if(productCategoryList.isEmpty())
                throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

            return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    Mapper.convertList(productCategoryList, ProductCategoryResponse.class));
        }
//        return new ApiResponse(AppStatus.REJECT.label, HttpStatus.EXPECTATION_FAILED.value(),
//                "You are not Authorized");
//    }



    @Override
    /**
     * @Validate that no duplicate productCategory is allowed
     * @Validate that product category exists otherwise return record not found
     * Create the product definition and save
     * @return success message* *
     * * */
    public ApiResponse<String> addProductCategory(ProductCategoryRequest request) {

        Optional<ProductType> productCategoryOptional = validateDuplicateProductCategory
                (request.getRoom(),request.getItems());

        AppUser existingUser  = userRepository.findByUuid(request.getCreatedById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        if (!productCategoryOptional.isEmpty()) {
            return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
                    "Duplicate Record");
        }

        ProductType productCategory = new ProductType();

        productCategory.setRoom(request.getRoom());
        productCategory.setItems(request.getItems());
        productCategory.setDescription(request.getDescription());
        productCategory.setCreatedBy(existingUser);

        productCategoryRepository.save(productCategory);

        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Added successfully");
    }

    /**
     * @Validating existingProductCategoryOptional by name
     * @Validate if the List of existingProductCategoryOptional is empty otherwise return Duplicate Record
     * */
    private Optional<ProductType> validateDuplicateProductCategory(String room, String items) {
        Optional<ProductType> existingProductCategoryOptional = productCategoryRepository.findProductCategoryByRoomAndItems(room, items);
        return existingProductCategoryOptional;

    }

    /**
     * Set and get the productCategory parameters
     */
    private ProductType getProductCategoryFromRequest(ProductCategoryRequest request) {
        ProductType productCategory = new ProductType();

        productCategory.setRoom(request.getRoom());
        productCategory.setItems(request.getItems());
        productCategory.setDescription(request.getDescription());

        return productCategory;
    }

    /**
     * @validating productCategoryOptional by uuid
     * @Validate if the List of productCategory is empty otherwise return record not found
     * @return productCategoryOptional
     * * */
    private ProductType validateProductCategory(UUID uuid){
        Optional<ProductType> productCategoryOptional = productCategoryRepository.findByUuid(uuid);
        if(productCategoryOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return productCategoryOptional.get();
    }


    @Override
    /**
     * @Validating and Finding the list of productCategoryOptional by uuid
     * @Validate if the List of productCategoryOptional is empty otherwise return record not found
     * Create the productCategory definition and get the productCategoryOptional by uuid
     * @return the list of productCategory and a Success Message
     * * */
    public ApiResponse<ProductCategoryResponse> getProductCategoryById(UUID productCategoryId) {
//        if(jwtFilter.isAdmin()){

            Optional<ProductType> productCategoryOptional = productCategoryRepository.findByUuid(productCategoryId);

            if(productCategoryOptional.isEmpty())
                throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

            ProductType productCategory = productCategoryOptional.get();

            return new ApiResponse<ProductCategoryResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    Mapper.convertObject(productCategory,ProductCategoryResponse.class));
        }
//        return new ApiResponse(AppStatus.REJECT.label, HttpStatus.EXPECTATION_FAILED.value(),
//                "You are not Authorized");
//    }


    @Override
    /**
     * @validating productCategoryOptional by uuid
     * @Validate if the List of productCategoryOptional is empty otherwise return record not found
     * Create the productCategory definition and update
     * @return a Success Message
     * * */
    public ApiResponse<String> updateProductCategory(UUID productCategoryId,ProductCategoryRequest request) {
//        if(jwtFilter.isAdmin()){

        ProductType productCategory = validateProductCategory(productCategoryId);

        productCategory.setRoom(request.getRoom());
        productCategory.setItems(request.getItems());
        productCategory.setDescription(request.getDescription());

        productCategoryRepository.save(productCategory);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    "Record Updated Successfully");
        }
//        return new ApiResponse(AppStatus.REJECT.label, HttpStatus.EXPECTATION_FAILED.value(),
//                "You are not Authorized");
//    }

    @Override
    /**
     * @validate ProductCategory by uuid
     * @Validate if ProductCategory is empty otherwise return record not found
     * @Delete ProductCategory
     * @return a Success Message
     * * */
    public ApiResponse<String> deleteProductCategory(UUID productCategoryId) {
//        if(jwtFilter.isAdmin()){

            ProductType productCategory = validateProductCategory(productCategoryId);
            productCategoryRepository.delete(productCategory);
            return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    "Record Deleted successfully");
        }
//        return new ApiResponse(AppStatus.REJECT.label, HttpStatus.EXPECTATION_FAILED.value(),
//                "You are not Authorized");
//    }


}
