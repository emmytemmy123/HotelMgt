package fcmb.com.good.services.products;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.productsRequest.ProductOrderRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.productsResponse.ProductOrderResponse;
import fcmb.com.good.model.entity.products.ProductOrder;
import fcmb.com.good.repo.products.ProductOrderRepository;
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
public class ProductOrderServiceImpl implements ProductOrderService {
    private  final ProductOrderRepository productOrderRepository;

    @Override
    public ApiResponse<List<ProductOrderResponse>> getListOfProductOrder(int page, int size) {
        List<ProductOrder> productOrderList = productOrderRepository.findAll(PageRequest.of(page,size)).toList();
        if(productOrderList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(productOrderList, ProductOrderResponse.class));
    }

    @Override
    public ApiResponse<String> addProductOrder(@RequestBody ProductOrderRequest request) {
        ProductOrder productOrder = Mapper.convertObject(request,ProductOrder.class);
        productOrder=productOrderRepository.save(productOrder);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Added Successsfully");
    }

    @Override
    public ApiResponse<ProductOrderResponse> getProductOrderById(@RequestParam("id") UUID productOrderId) {
        Optional<ProductOrder> productOrder = productOrderRepository.findByUuid(productOrderId);

        if(productOrder.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        ProductOrder po = productOrder.get();
        return new ApiResponse<ProductOrderResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(), Mapper.convertObject(po,ProductOrderResponse.class));

    }

    private ProductOrder validateProductOrder(UUID uuid){
        Optional<ProductOrder> productOrder = productOrderRepository.findByUuid(uuid);
        if(productOrder.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return productOrder.get();
    }

    @Override
    public ApiResponse<String> updateProductOrder(UUID productOderId, @RequestBody ProductOrderRequest request) {
        ProductOrder productOrder = validateProductOrder(productOderId);
        productOrder.setCustomer_id(request.getCustomer_id());
        productOrder.setProduct_id(request.getProduct_id());
        productOrder.setAmount(request.getAmount());
        productOrder.setTax(request.getTax());
        productOrder.setOrder_no(request.getOrder_no());
        productOrder.setAccount_no(request.getAccount_no());
        productOrder.setProfit(request.getProfit());
        productOrder.setSales_person(request.getSales_person());
        productOrder.setOrder_state(request.getOrder_state());

        productOrder = productOrderRepository.save(productOrder);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Updated Successfully");
    }

    @Override
    public ApiResponse<String> deleteProductOrder(UUID productOrderId) {
        ProductOrder productOrder = validateProductOrder(productOrderId);
        productOrderRepository.delete(productOrder);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");

    }


}
