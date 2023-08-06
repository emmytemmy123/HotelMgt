package fcmb.com.good.services.products;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.productsRequest.ProductFacilityRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.productsResponse.ProductFacilityResponse;
import fcmb.com.good.model.entity.products.Product;
import fcmb.com.good.model.entity.products.ProductFacility;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.repo.products.ProductFacilityRepository;
import fcmb.com.good.repo.products.ProductRepository;
import fcmb.com.good.repo.user.CustomerRepository;
import fcmb.com.good.repo.user.UserRepository;
import fcmb.com.good.utills.MessageUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductFacilityServiceImpl implements ProductFacilityService {

    private final ProductFacilityRepository productFacilityRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;


    /**
     * @Validating existingRoomFacilityOptional by name
     * @Validating existingRoomCategoryOptional by name
     * @Validate the List of existingRoomFacilityOptional otherwise return Duplicate Record
     * * */
    private void validateDuplicationProductFacility(String name){
        Optional<ProductFacility> productFacilityOptional = productFacilityRepository.findByName(name);

        if(productFacilityOptional.isPresent() )
            throw new RecordNotFoundException("Duplicate record");
    }

    @Override
    /**
     * @Validate that no duplicate roomFacilityName is allowed
     * @Validate that user creating the roomFacility exists, otherwise return user not found
     * Create the roomFacility definition and save
     * @return success message
     * * */
    public ApiResponse<String> addProductFacility(ProductFacilityRequest request) {

        validateDuplicationProductFacility(request.getName());

        AppUser existingUser  = userRepository.findByUuid(request.getCreatedById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Product existingProduct  = productRepository.findByUuid(request.getProductId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        ProductFacility productFacility = new ProductFacility();

        productFacility.setName(request.getName());
        productFacility.setFileName(request.getFileName());
        productFacility.setQuantity(request.getQuantity());
        productFacility.setStatus("Good Condition");
        productFacility.setDescription(request.getDescription());
        productFacility.setCreatedBy(existingUser);
        productFacility.setProduct(existingProduct);

        productFacilityRepository.save(productFacility);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record created successfully");

    }


    @Override
    /**
     * @Finding the list of all roomFacilityOptional by uuid
     * @Validate if the List of roomFacilityOptional is empty otherwise return record not found
     * Create the roomFacility definition and get the roomFacilityOptional by uuid
     * @return the list of roomFacility and a Success Message
     * * */
    public ApiResponse<ProductFacilityResponse> getProductFacilityById(UUID productFacilityId) {
        Optional<ProductFacility> productFacilityOptional = productFacilityRepository.findByUuid(productFacilityId);

        if(productFacilityOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        ProductFacility productFacility = productFacilityOptional.get();

        return new ApiResponse<ProductFacilityResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(productFacility, ProductFacilityResponse.class));
    }


    /**
     * @validating roomFacility by uuid
     * @Validate if the List of roomFacility is empty otherwise return record not found
     * @return the list of roomFacility
     * * */
    private ProductFacility validateProductFacility(UUID uuid){
        Optional<ProductFacility> productFacilityOptional = productFacilityRepository.findByUuid(uuid);
        if(productFacilityOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return productFacilityOptional.get();
    }


    @Override
    /**
     * @Validating the list of existingRoomFacility by uuid
     * @Validate if the List of existingRoomFacility is empty otherwise return record not found
     * Create the roomFacility definition and save
     * @return a Success Message
     * * */
    public ApiResponse<String> updateProductFacility(UUID productFacilityId, ProductFacilityRequest request) {

        ProductFacility productFacility = validateProductFacility(productFacilityId);
        productFacility.setName(request.getName());
        productFacility.setFileName(request.getFileName());
        productFacility.setDescription(request.getDescription());

        productFacilityRepository.save(productFacility);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Updated successfully");
    }

    @Override
    /**
     * @validating roomFacility by uuid
     * @Validate if roomFacility is empty otherwise return record not found
     * @Delete roomFacility
     * @return a Success Message
     * * */
    public ApiResponse<String> deleteProductFacility(UUID productFacilityId) {
        ProductFacility productFacility = validateProductFacility(productFacilityId);
        productFacilityRepository.delete(productFacility);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }


    @Override
    /**
     * @Search the list of all RoomFacilityResponse by roomNo
     * @Validate if the List of RoomFacilityResponse is empty otherwise return record not found*
     * @return the list of RoomFacilityResponse by roomNo
     * * */
    public ApiResponse<List<ProductFacilityResponse>> getProductFacilityByProduct(UUID productUuid) {

    List<ProductFacility> productFacilityList = productFacilityRepository.findProductFacilityByProduct(productUuid);

    if(productFacilityList.isEmpty())
        throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(productFacilityList, ProductFacilityResponse.class));

    }



}
