package fcmb.com.good.services.products;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.productsRequest.ProductPurchaseRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.productsResponse.ProductPurchaseResponse;
import fcmb.com.good.model.dto.response.userResponse.CustomerResponse;
import fcmb.com.good.model.entity.products.ProductPurchase;
import fcmb.com.good.model.entity.user.Customer;
import fcmb.com.good.repo.products.ProductPurchaseRepository;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductPurchaseServiceImpl implements ProductPurchaseService {
    private  final ProductPurchaseRepository productPurchaseRepository;

    @Override
    public ApiResponse<List<ProductPurchaseResponse>> getListOfProductPurchase(int page, int size) {
        List<ProductPurchase> productPurchaseList = productPurchaseRepository.findAll(PageRequest.of(page,size)).toList();
        if(productPurchaseList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(productPurchaseList, ProductPurchaseResponse.class));
    }

    @Override
    public ApiResponse<ProductPurchaseResponse> addProductPurchase(@RequestBody ProductPurchaseRequest request) {
        ProductPurchase productPurchase = Mapper.convertObject(request,ProductPurchase.class);
        productPurchase=productPurchaseRepository.save(productPurchase);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(productPurchase,ProductPurchaseResponse.class));
    }

    @Override
    public ApiResponse<ProductPurchaseResponse> getProductPurchaseById(@RequestParam("id") UUID productPurchaseId) {
        Optional<ProductPurchase> productPurchase = productPurchaseRepository.findByUuid(productPurchaseId);

        if(productPurchase.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        ProductPurchase pc = productPurchase.get();
        return new ApiResponse<ProductPurchaseResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(pc,ProductPurchaseResponse.class));
    }

    private ProductPurchase validateProductPurchase(UUID uuid){
        Optional<ProductPurchase> productPurchase = productPurchaseRepository.findByUuid(uuid);
        if(productPurchase.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return productPurchase.get();
    }

    @Override
        public ApiResponse<ProductPurchaseResponse> updateProductPurchase(@RequestParam UUID productPurchaseId,
                                                                          @RequestBody ProductPurchaseRequest request) {
        ProductPurchase productPurchase = validateProductPurchase(productPurchaseId);
        productPurchase.setProduct_id(request.getProduct_id());
        productPurchase.setDescription(request.getDescription());
        productPurchase.setCompany_name(request.getCompany_name());
        productPurchase.setQuantity(request.getQuantity());
        productPurchase.setPrice(request.getPrice());

        productPurchase = productPurchaseRepository.save(productPurchase);
        return new ApiResponse<ProductPurchaseResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(productPurchase,ProductPurchaseResponse.class));
    }

    @Override
    public ApiResponse<String>  deleteProductPurchase(UUID productPurchaseId) {
        ProductPurchase productPurchase = validateProductPurchase(productPurchaseId);
        productPurchaseRepository.delete(productPurchase);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }



}
