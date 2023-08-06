package fcmb.com.good.services.products;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.productsRequest.ProductPurchaseRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.productsResponse.ProductPurchaseResponse;
import fcmb.com.good.model.entity.products.Product;
import fcmb.com.good.model.entity.products.ProductType;
import fcmb.com.good.model.entity.products.ProductPurchase;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.repo.products.ProductCategoryRepository;
import fcmb.com.good.repo.products.ProductPurchaseRepository;
import fcmb.com.good.repo.products.ProductRepository;
import fcmb.com.good.repo.user.UserRepository;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service

@RequiredArgsConstructor
public class ProductPurchaseServiceImpl implements ProductPurchaseService {

    private  final ProductPurchaseRepository productPurchaseRepository;
    private  final ProductCategoryRepository productCategoryRepository;
    private  final UserRepository userRepository;
    private  final ProductRepository productRepository;


    @Override
    /**
     * @Finding the list of all productPurchase
     * @Validate if the List of productPurchase is empty otherwise return record not found*
     * @return the list of productPurchase and a Success Message* *
     * * */
    public ApiResponse<List<ProductPurchaseResponse>> getListOfProductPurchase(int page, int size) {
        List<ProductPurchase> productPurchaseList = productPurchaseRepository.findAll(PageRequest.of(page,size)).toList();
        if(productPurchaseList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(productPurchaseList, ProductPurchaseResponse.class));
    }


    /**
     * @validating productPurchase by uuid*
     * @Validate if productPurchase is empty otherwise return record not found
     * @return productPurchase
     * * */
    private ProductPurchase validateProductPurchase(UUID uuid){
        Optional<ProductPurchase> productPurchase = productPurchaseRepository.findByUuid(uuid);
        if(productPurchase.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return productPurchase.get();
    }

    /**
     * @Validating existingProductPurchaseOptional by name
     * @Validating existingProductCategoryOptional by productName
     * @Validate the List of existingProductPurchaseOptional and existingProductCategoryOptional is present otherwise return Duplicate Record*
     * * */
    private void validateDuplicationProductPurchase(String name){
        Optional<ProductPurchase> existingProductPurchaseOptional = productPurchaseRepository.findByName(name);

        if(existingProductPurchaseOptional.isPresent() )
            throw new RecordNotFoundException("Duplicate record");
    }


    @Override
    /**
     * @Validate that no duplicate ProductPurchase allow*
     * @Validate that ProductPurchase exists otherwise return record not found*
     * @Validate that user creating the ProductPurchase exists, otherwise return user not found*
     * Create the ProductPurchase definition and save
     * @return success message* *
     * * */
    public ApiResponse<String> addProductPurchase(ProductPurchaseRequest request) {

        validateDuplicationProductPurchase(request.getName());

        ProductType existingProductCategory = productCategoryRepository.findByUuid(request.getProductCategoryId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        AppUser existingUser  = userRepository.findByUuid(request.getCreatedBy())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Product existingProduct  = productRepository.findByUuid(request.getProductId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        ProductPurchase productPurchase = new ProductPurchase();

        productPurchase.setName(request.getName());
        productPurchase.setCategory(request.getCategory());
        productPurchase.setDescription(request.getDescription());
        productPurchase.setCompanyName(request.getCompanyName());
        productPurchase.setBrand(request.getBrand());
        productPurchase.setPrice(request.getPrice());
        productPurchase.setProductPurchaseDate(request.getProductPurchaseDate());
        productPurchase.setQuantity(String.valueOf(Integer.sum(existingProduct.getQuantity(), Integer.parseInt(request.getQuantity()))));
        existingProduct.setQuantity(Integer.sum(existingProduct.getQuantity(), Integer.parseInt(request.getQuantity())));
        productPurchase.setCreatedBy(existingUser);
        productPurchase.setProduct(existingProduct);
        productPurchase.setProductCategory(existingProductCategory);
        productPurchaseRepository.save(productPurchase);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Added Successfully");
    }


    @Override
    /**
     * @Finding the list of all productPurchaseOptional by uuid*
     * @Validate if the List of productPurchaseOptional is empty otherwise return record not found
     * Create the productPurchase definition and get the product Optional by uuid
     * @return the list of productPurchase and a Success Message* *
     * * */
    public ApiResponse<ProductPurchaseResponse> getProductPurchaseById( UUID productPurchaseId) {
        Optional<ProductPurchase> productPurchaseOptional = productPurchaseRepository.findByUuid(productPurchaseId);

        if(productPurchaseOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        ProductPurchase productPurchase = productPurchaseOptional.get();

        return new ApiResponse<ProductPurchaseResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(productPurchase,ProductPurchaseResponse.class));
    }



    @Override
    /**
     * @Validating the list of existingProductPurchase by uuid*
     * @Validate if the List of existingProductPurchase is empty otherwise return record not found
     * Create the existingProductPurchase definition and save
     * @return a Success Message* *
     * * */
        public ApiResponse<String> updateProductPurchase( UUID productPurchaseId, ProductPurchaseRequest request) {

        ProductPurchase productPurchase = validateProductPurchase(productPurchaseId);
        Product existingProduct  = productRepository.findByUuid(request.getProductId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));


        productPurchase.setName(request.getName());
        productPurchase.setCategory(request.getCategory());
        productPurchase.setDescription(request.getDescription());
        productPurchase.setCompanyName(request.getCompanyName());
        productPurchase.setBrand(request.getBrand());

        productPurchase.setPrice(request.getPrice());
        productPurchase.setProductPurchaseDate(request.getProductPurchaseDate());
        productPurchase.setQuantity(String.valueOf(Integer.sum(existingProduct.getQuantity(), Integer.parseInt(request.getQuantity()))));
        existingProduct.setQuantity(Integer.sum(existingProduct.getQuantity(), Integer.parseInt(request.getQuantity())));

        productPurchaseRepository.save(productPurchase);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Updated Successfully");
    }

    @Override
    public ApiResponse<String>  deleteProductPurchase(UUID productPurchaseId) {
        ProductPurchase productPurchase = validateProductPurchase(productPurchaseId);

        productPurchaseRepository.delete(productPurchase);

        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }

    @Override
    /**
     * @validating productPurchase by uuid*
     * @Validate if productPurchase is empty otherwise return record not found
     * @Delete productPurchase
     * @return a Success Message* *
     * * */
    public ApiResponse<List<ProductPurchaseResponse>> searchProductPurchaseByName(String name) {

        List<ProductPurchase> searchProductPurchaseByName = productPurchaseRepository.searchProductPurchaseByName(name);

        if(searchProductPurchaseByName.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(searchProductPurchaseByName, ProductPurchaseResponse.class));


    }


    @Override
    public ApiResponse<List<ProductPurchaseResponse>> findByDateCreatedBetween(LocalDateTime dateCreated, LocalDateTime lastModified) {

        List<ProductPurchase> searchProductPurchaseByDateRange = productPurchaseRepository.findByDateCreatedBetween(dateCreated,lastModified);

        if(searchProductPurchaseByDateRange.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(searchProductPurchaseByDateRange, ProductPurchaseResponse.class));

    }


}
