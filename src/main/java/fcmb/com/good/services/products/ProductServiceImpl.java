package fcmb.com.good.services.products;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.filter.JwtFilter;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.productsRequest.ProductRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.productsResponse.ProductResponse;
import fcmb.com.good.model.entity.products.Product;
import fcmb.com.good.model.entity.products.ProductType;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.repo.products.ProductCategoryRepository;
import fcmb.com.good.repo.products.ProductRepository;
import fcmb.com.good.repo.user.UserRepository;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private  final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final JwtFilter jwtFilter;
    private final UserRepository userRepository;

    @Override
    /**
     * @Finding the list of all products*
     * @Validate if the List of products is empty otherwise return record not found*
     * @return the list of products and a Success Message* *
     * * */
    public ApiResponse<List<ProductResponse>> getListOfProduct(int page, int size) {
//        if(jwtFilter.isAdmin() || jwtFilter.isEmployee()) {

            List<Product> productList = productRepository.findAll(PageRequest.of(page,size)).toList();
            if(productList.isEmpty())
                throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(productList, ProductResponse.class));

//        }
//        return new ApiResponse(AppStatus.REJECT.label, HttpStatus.EXPECTATION_FAILED.value(),
//                "You are not Authorized");
    }


    @Override
    /**
     * @Search the list of all products by name
     * @Validate if the List of products is empty otherwise return record not found*
     * @return the list of products by name* *
     * * */
    public ApiResponse<List<ProductResponse>> searchProductsByName(String name) {
        List<Product> searchProductsByName = productRepository.searchProductsByName(name);

        if(searchProductsByName.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(searchProductsByName, ProductResponse.class));

    }

//    @Override
//    /**
//     * @Search the list of all products by category*
//     * @Validate if the List of productCategory is empty otherwise return record not found*
//     * @return the list of products by categoryName* *
//     * * */
//    public ApiResponse<List<ProductResponse>> searchProductsByProductCategory(String productCategory) {
//        List<Product> searchProductsByProductCategory = productRepository.searchProductsByProductCategory(productCategory);
//
//        if(searchProductsByProductCategory.isEmpty())
//            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
//
//        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
//                Mapper.convertList(searchProductsByProductCategory, ProductResponse.class));
//
//    }


    @Override
    /**
     * @Finding the list of all productOptional by uuid*
     * @Validate if the List of productOptional is empty otherwise return record not found
     * Create the product definition and get the product Optional by uuid
     * @return the list of products and a Success Message* *
     * * */
    public ApiResponse<ProductResponse> getProductById(UUID productId) {
//        if(jwtFilter.isAdmin()){

        Optional<Product> productOptional = productRepository.findByUuid(productId);

        if(productOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        Product product = productOptional.get();

        return new ApiResponse<ProductResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(product,ProductResponse.class));
    }
//        return new ApiResponse(AppStatus.REJECT.label, HttpStatus.EXPECTATION_FAILED.value(),
//                "You are not Authorized");
//    }

    /**
     * @validating products by uuid*
     * @Validate if products is empty otherwise return record not found
     * @return product
     * * */
    private Product validateProducts(UUID uuid){
        return productRepository.findByUuid(uuid).orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));
    }

    /**
     * @Validating existingProductOptional by name*
     * @Validate if the List of existingProductOptional is empty otherwise return Duplicate Record*
     * * */
    private void validateDuplicationProduct(String name){
        Optional<Product> existingProductOptional = productRepository.findByName(name);
        if(existingProductOptional.isPresent())
            throw new RecordNotFoundException("Duplicate record");
    }


    @Override
    /**
     * @Validate that no duplicate product allow*
     * @Validate that product category exists otherwise return record not found*
     * @Validate that user creating the product exists, otherwise return user not found*
     * Create the product definition and save
     * @return success message* *
     * * */
    public ApiResponse<String> addProducts(ProductRequest request) {

        validateDuplicationProduct(request.getName());

        ProductType existingProductType = productCategoryRepository.findByUuid(request.getCategoryId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        AppUser existingUser  = userRepository.findByUuid(request.getCreatedById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Product product = new Product();

        product.setCategory(request.getCategory());
        product.setBrand(request.getBrand());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setQuantity(request.getQuantity());
        product.setSalesPrice(request.getSalesPrice());
        product.setDurations(request.getDurations());
        product.setPurchasePrice(request.getPurchasedPrice());
        product.setExpDate(request.getExpDate());
        product.setPostedBy(existingUser.getName());
        product.setProductType(existingProductType);

        productRepository.save(product);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record created successfully");
    }


    @Override
    /**
     * @Validating the list of existingProductCategory by uuid*
     * @Validate if the List of existingProductCategory is empty otherwise return record not found
     * Create the product definition and save
     * @return a Success Message* *
     * * */
    public ApiResponse<String> updateProduct(UUID productId, ProductRequest request) {
//        if(jwtFilter.isAdmin()){

        Product product = validateProducts(productId);

        product.setCategory(request.getCategory());
        product.setBrand(request.getBrand());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setQuantity(product.getQuantity()+request.getQuantity());
        product.setSalesPrice(request.getSalesPrice());
        product.setDurations(request.getDurations());
        product.setPurchasePrice(request.getPurchasedPrice());
        product.setExpDate((Date) request.getExpDate());

        productRepository.save(product);
        return new ApiResponse<String>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    "Record updated successfully");
        }
//        return new ApiResponse(AppStatus.REJECT.label, HttpStatus.EXPECTATION_FAILED.value(),
//                "You are not Authorized");
//    }


    @Override
    /**
     * @validating products by uuid*
     * @Validate if products is empty otherwise return record not found
     * @Delete product
     * @return a Success Message* *
     * * */
    public ApiResponse<String> deleteProduct(UUID productId) {
//        if(jwtFilter.isAdmin()){
            Product product = validateProducts(productId);
            productRepository.delete(product);
            return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    "Record Deleted successfully");
        }
//        return new ApiResponse(AppStatus.REJECT.label, HttpStatus.EXPECTATION_FAILED.value(),
//                "You are not Authorized");
//    }


}
