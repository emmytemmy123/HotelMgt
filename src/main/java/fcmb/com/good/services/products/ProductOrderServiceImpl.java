package fcmb.com.good.services.products;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.productsRequest.ProductOrderRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.productsResponse.ProductOrderResponse;
import fcmb.com.good.model.entity.products.Product;
import fcmb.com.good.model.entity.products.ProductCategory;
import fcmb.com.good.model.entity.products.ProductOrder;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.model.entity.user.Customer;
import fcmb.com.good.model.entity.user.Employee;
import fcmb.com.good.repo.products.ProductOrderRepository;
import fcmb.com.good.repo.products.ProductRepository;
import fcmb.com.good.repo.user.CustomerRepository;
import fcmb.com.good.repo.user.EmployeeRepository;
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
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;


    @Override
    /**
     * @Finding the list of all productsOrder
     * @Validate if the List of productsOrder is empty otherwise return record not found*
     * @return the list of productsOrder and a Success Message* *
     * * */
    public ApiResponse<List<ProductOrderResponse>> getListOfProductOrder(int page, int size) {
        List<ProductOrder> productOrderList = productOrderRepository.findAll(PageRequest.of(page,size)).toList();
        if(productOrderList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(productOrderList, ProductOrderResponse.class));
    }


    /**
     * @Validating existingProductOrderOptional by name*
     * @Validate if the List of existingProductOrderOptional is empty otherwise return Duplicate Record*
     * * */
    private void validateDuplicationProductOrder(Integer orderNo){
        Optional<ProductOrder> existingProductOrderOptional = productOrderRepository.findByOrderNo(orderNo);
        if(existingProductOrderOptional.isPresent())
            throw new RecordNotFoundException("Duplicate record");
    }


    @Override
    /**
     * @Search the list of all ProductOrder by name
     * @Validate if the List of ProductOrder is empty otherwise return record not found*
     * @return the list of ProductOrder by name* *
     * * */
    public ApiResponse<String> addProductOrder(ProductOrderRequest request) {

//        validateDuplicationProductOrder(Integer.valueOf(request.getOrderNo()));

        Employee existingEmployee = employeeRepository.findByUuid(request.getEmployeeId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Customer existingCustomer  = customerRepository.findByUuid(request.getCustomerId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Product existingProduct  = productRepository.findByUuid(request.getProductId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        ProductOrder productOrder = new ProductOrder();
        productOrder.setProductName(request.getProductName());
        productOrder.setAmount(request.getQuantity()* request.getPrice());
        productOrder.setTax(request.getTax());
        productOrder.setOrderNo(request.getOrderNo());
        productOrder.setAccountNo(request.getAccountNo());
        productOrder.setQuantity(request.getQuantity());
        productOrder.setPrice(request.getPrice());
        productOrder.setProfit((request.getPrice() - existingProduct.getPurchasedPrice()) * (request.getQuantity()) );
        productOrder.setSalesPerson(existingEmployee.getName());
        productOrder.setOrderState(request.getOrderState());
        productOrder.setEmployee(existingEmployee );
        productOrder.setCustomer(existingCustomer);
        productOrder.setProduct(existingProduct);
        productOrder.setCurrentCustomer(existingCustomer.getName());

        productOrderRepository.save(productOrder);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record created successfully");
    }


    @Override
    /**
     * @Finding the list of all productOrderOptional by uuid*
     * @Validate if the List of productOrderOptional is empty otherwise return record not found
     * Create the productOrder definition and get the productOrderOptional by uuid
     * @return the list of productOrder and a Success Message* *
     * * */
    public ApiResponse<ProductOrderResponse> getProductOrderById(UUID productOrderId) {
        Optional<ProductOrder> productOrderOptional = productOrderRepository.findByUuid(productOrderId);

        if(productOrderOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        ProductOrder productOrder = productOrderOptional.get();

        return new ApiResponse<ProductOrderResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(productOrder,ProductOrderResponse.class));

    }

    /**
     * @validating productsOrder by uuid*
     * @Validate if productsOrder is empty otherwise return record not found
     * @return productsOrder
     * * */
    private ProductOrder validateProductOrder(UUID uuid){
        Optional<ProductOrder> productOrderOptional = productOrderRepository.findByUuid(uuid);

        if(productOrderOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return productOrderOptional.get();
    }

    @Override
    /**
     * @Validating the list of existingProductOrder by uuid*
     * @Validate if the List of existingProductOrder is empty otherwise return record not found
     * Create the existingProductOrder definition and save
     * @return a Success Message
     * * */
    public ApiResponse<String> updateProductOrder(UUID productOderId, @RequestBody ProductOrderRequest request) {
        ProductOrder productOrder = validateProductOrder(productOderId);

        productOrder.setProductName(request.getProductName());
        productOrder.setAmount(request.getAmount());
        productOrder.setTax(request.getTax());
        productOrder.setOrderNo(request.getOrderNo());
        productOrder.setAccountNo(request.getAccountNo());
        productOrder.setProfit(request.getProfit());
        productOrder.setOrderState(request.getOrderState());

        productOrderRepository.save(productOrder);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Updated Successfully");
    }

    @Override
    /**
     * @validating productOrder by uuid*
     * @Validate if productOrder is empty otherwise return record not found
     * @Delete productOrder
     * @return a Success Message* *
     * * */
    public ApiResponse<String> deleteProductOrder(UUID productOrderId) {
        ProductOrder productOrder = validateProductOrder(productOrderId);
        productOrderRepository.delete(productOrder);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");

    }





}
