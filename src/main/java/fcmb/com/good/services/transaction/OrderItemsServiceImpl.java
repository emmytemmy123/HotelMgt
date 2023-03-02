package fcmb.com.good.services.transaction;


import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.transactionRequest.OrderItemRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.entity.products.Product;
import fcmb.com.good.model.entity.products.ProductType;
import fcmb.com.good.model.entity.transaction.OrderItems;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.repo.products.ProductCategoryRepository;
import fcmb.com.good.repo.products.ProductRepository;
import fcmb.com.good.repo.transaction.OrderItemsRepository;
import fcmb.com.good.repo.user.UserRepository;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class OrderItemsServiceImpl implements OrderItemsService{

    private final OrderItemsRepository orderItemsRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    /**
     * @Validating existingOrderItemsOptional by itemName
     * @Validating existingOrderItemsOptional by itemName
     * @Validate the List of existingOrderItems and existingOrderItemsOptional is present otherwise return Duplicate Record*
     * * */
    private void validateDuplicateOrderItems(String items){

        Optional<OrderItems> orderItemsOptional = orderItemsRepository.findByItemName(items);

        if(orderItemsOptional.isPresent() )
            throw new RecordNotFoundException("Duplicate record");
    }


    @Override
    /**
     * @Validate that no duplicate OrderItems is allowed
     * @Validate that OrderItems exists otherwise return record not found
     * Create the OrderItems definition and save
     * @return success message
     * * */
    public ApiResponse<String> addOrderItems(OrderItemRequest request) {

        validateDuplicateOrderItems(request.getItemName());

        AppUser existingUser  = userRepository.findByUuid(request.getCreatedById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Product existingProduct  = productRepository.findByUuid(request.getProductById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        OrderItems orderItems = new OrderItems();

        orderItems.setItemName(existingProduct.getName());
        orderItems.setSalesPrice(existingProduct.getSalesPrice());
        orderItems.setQuantity(request.getQuantity());
        orderItems.setAmount((existingProduct.getSalesPrice() * (request.getQuantity())));
        orderItems.setPurchasePrice(existingProduct.getPurchasePrice());
        orderItems.setPurchaseAmount((existingProduct.getPurchasePrice())*(request.getQuantity()));
        orderItems.setProfit((orderItems.getAmount())-(orderItems.getPurchaseAmount()));
        orderItems.setServiceName(request.getServiceName());
        orderItems.setDescription(request.getDescription());
        orderItems.setTransactionDate(request.getTransactionDate());
        orderItems.setStatus(request.getStatus());
        orderItems.setCreatedBy(existingUser);
        orderItems.setProduct(existingProduct);

        orderItemsRepository.save(orderItems);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record created successfully");

    }



    @Override
    public ApiResponse<String> updateOrderItems(UUID orderItemsId, OrderItemRequest request) {
        return null;
    }



    @Override
    public ApiResponse<String> deleteOrderItems(UUID orderItemsId) {
        return null;
    }
}
