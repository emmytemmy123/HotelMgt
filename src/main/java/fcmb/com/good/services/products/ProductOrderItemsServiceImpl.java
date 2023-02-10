package fcmb.com.good.services.products;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.productsRequest.ProductOrderItemsRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.productsResponse.ProductOrderItemsResponse;
import fcmb.com.good.model.entity.products.ProductOrderItems;
import fcmb.com.good.repo.products.ProductOrderItemsRepository;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductOrderItemsServiceImpl implements ProductOrderItemsService {
    private  final ProductOrderItemsRepository productOrderItemsRepository;

    @Override
    public ApiResponse<List<ProductOrderItemsResponse>> getListOfProductOrderItems(int page, int size) {
        List<ProductOrderItems> productOrderItemsList = productOrderItemsRepository.findAll(PageRequest.of(page,size)).toList();
        if(productOrderItemsList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(productOrderItemsList, ProductOrderItemsResponse.class));
    }

    @Override
    public ApiResponse<String> addProductOrderItems(@RequestBody ProductOrderItemsRequest request) {
        ProductOrderItems productOrderItems = Mapper.convertObject(request,ProductOrderItems.class);
        productOrderItems=productOrderItemsRepository.save(productOrderItems);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record added Successfully");
    }

    @Override
    public ApiResponse<ProductOrderItemsResponse> getProductOrderItemsById(@RequestParam("id") UUID productOrderItemsId) {
        Optional<ProductOrderItems> productOrderItems = productOrderItemsRepository.findByUuid(productOrderItemsId);

        if(productOrderItems.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        ProductOrderItems prt = productOrderItems.get();
        return new ApiResponse<ProductOrderItemsResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(), Mapper.convertObject(prt,ProductOrderItemsResponse.class));

    }

    private ProductOrderItems validateProductOrderItems(UUID uuid){
        Optional<ProductOrderItems> productOrderItems = productOrderItemsRepository.findByUuid(uuid);
        if(productOrderItems.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return productOrderItems.get();
    }

    @Override
    public ApiResponse<String> updateProductOrderItems(UUID productOderItemsId,
                                                                          @RequestBody ProductOrderItemsRequest request) {
        Optional<ProductOrderItems> productOrderItems = productOrderItemsRepository.findByUuid(productOderItemsId);
        ProductOrderItems productOrderItems1 = validateProductOrderItems(productOderItemsId);
        productOrderItems1.setProduct_id(request.getProduct_id());
        productOrderItems1.setProduct_order_id(request.getProduct_order_id());
        productOrderItems1.setProduct_name(request.getProduct_name());
        productOrderItems1.setQuantity(request.getQuantity());
        productOrderItems1.setPrice(request.getPrice());

        productOrderItems1 = productOrderItemsRepository.save(productOrderItems1);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Added Successfully");
    }


    @Override
    public ApiResponse<String> deleteProductOrderItems(UUID productOrderItemsId) {
        ProductOrderItems productOrderItems = validateProductOrderItems(productOrderItemsId);
        productOrderItemsRepository.delete(productOrderItems);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }


}
