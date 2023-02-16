package fcmb.com.good.services.products;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.productsRequest.ProductPurchaseRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.productsResponse.ProductPurchaseResponse;
import fcmb.com.good.model.entity.products.Product;
import fcmb.com.good.model.entity.products.ProductCategory;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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
    public ApiResponse<List<ProductPurchaseResponse>> getListOfProductPurchase(int page, int size) {
        List<ProductPurchase> productPurchaseList = productPurchaseRepository.findAll(PageRequest.of(page,size)).toList();
        if(productPurchaseList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(productPurchaseList, ProductPurchaseResponse.class));
    }


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
    public ApiResponse<String> addProductPurchase(@RequestBody ProductPurchaseRequest request) {

        validateDuplicationProductPurchase(request.getName());

        ProductCategory existingProductCategory = productCategoryRepository.findByUuid(request.getProductCategory())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        AppUser existingUser  = userRepository.findByUuid(request.getCreatedBy())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Product existingProduct  = productRepository.findByUuid(request.getProduct())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        ProductPurchase productPurchase = new ProductPurchase();
        productPurchase.setName(request.getName());
        productPurchase.setCategory(request.getCategory());
        productPurchase.setDescription(request.getDescription());
        productPurchase.setCompany_name(request.getCompanyName());
        productPurchase.setPrice(request.getPrice());
        productPurchase.setProductPurchaseDate(request.getProductPurchaseDate());
        productPurchase.setQuantity(request.getQuantity() + existingProduct.getQuantity());
        productPurchase.setCreatedBy(existingUser);
        productPurchase.setProduct(existingProduct);
        productPurchase.setProductCategory(existingProductCategory);
        productPurchaseRepository.save(productPurchase);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Added Successfully");
    }


    @Override
    public ApiResponse<ProductPurchaseResponse> getProductPurchaseById(@RequestParam("id") UUID productPurchaseId) {
        Optional<ProductPurchase> productPurchaseOptional = productPurchaseRepository.findByUuid(productPurchaseId);

        if(productPurchaseOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        ProductPurchase productPurchase = productPurchaseOptional.get();

        return new ApiResponse<ProductPurchaseResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(productPurchase,ProductPurchaseResponse.class));
    }



    @Override
        public ApiResponse<String> updateProductPurchase(@RequestParam UUID productPurchaseId,
                                                         @RequestBody ProductPurchaseRequest request) {
        ProductPurchase productPurchase = validateProductPurchase(productPurchaseId);

        productPurchase.setName(request.getName());
        productPurchase.setCategory(request.getCategory());
        productPurchase.setDescription(request.getDescription());
        productPurchase.setCompany_name(request.getCompanyName());
        productPurchase.setPrice(request.getPrice());
        productPurchase.setProductPurchaseDate(request.getProductPurchaseDate());
        productPurchase.setQuantity(productPurchase.getQuantity()+request.getQuantity());

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
