package fcmb.com.good.services.transaction;

import fcmb.com.good.dto.ApiResponse;
import fcmb.com.good.model.dto.request.orderItemRequest.OrderItemsRequest;
import fcmb.com.good.model.entity.transaction.OrderItems;
import fcmb.com.good.repo.orderItem.OrderItemRepo;
import fcmb.com.good.utills.MessageUtil;
import fcmb.com.good.utills.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService{
    private final OrderItemRepo orderItemRepo;



    @Override
    public ApiResponse addOrderItem(OrderItemsRequest payload) {
        //OrderItems item = orderItemRepo.getById()
        OrderItems orderItem = new OrderItems();
        orderItem.setItemName(payload.getItemName());
        orderItem.setDescription(payload.getDescription());
        orderItem.setPurchasePrice(payload.getPurchasePrice());
        orderItem.setQuantity(payload.getQuantity());
        orderItem.setSalesPrice(payload.getSalesPrice());
        orderItem.setServiceName(payload.getServiceName());
        orderItem.setAmount(payload.getAmount());
        orderItem.setPurchaseAmount(payload.getPurchaseAmount());

        orderItemRepo.save(orderItem);
        return new ApiResponse<>(MessageUtil.SUCCESS, ResponseCode.SUCCESS,orderItem);

    }

    @Override
    public ApiResponse updateOrderItem(Long itemId, OrderItemsRequest payload) {
        Optional<OrderItems> orderItem = orderItemRepo.findById(itemId);
        OrderItems orderItems = orderItem.get();

        if(Objects.nonNull(payload.getItemName()) && ! "".equalsIgnoreCase(payload.getItemName())){
            orderItems.setItemName(payload.getItemName());
        }
        if(Objects.nonNull(payload.getSalesPrice())){
            orderItems.setSalesPrice(payload.getSalesPrice());
        }
        if(Objects.nonNull(payload.getQuantity())){
            orderItems.setQuantity(payload.getQuantity());
        }
        if(Objects.nonNull(payload.getAmount())){
            orderItems.setAmount(payload.getAmount());
        }
        if(Objects.nonNull(payload.getPurchaseAmount())){
            orderItems.setPurchaseAmount(payload.getPurchaseAmount());
        }
        if(Objects.nonNull(payload.getPurchasePrice())){
            orderItems.setPurchasePrice(payload.getPurchasePrice());
        }
        if(Objects.nonNull(payload.getDescription()) && ! "".equalsIgnoreCase(payload.getDescription())){
            orderItems.setDescription(payload.getDescription());
        }
        if(Objects.nonNull(payload.getDescription()) && ! "".equalsIgnoreCase(payload.getDescription())){
            orderItems.setDescription(payload.getDescription());
        }

        if(Objects.nonNull(payload.getServiceName()) && ! "".equalsIgnoreCase(payload.getServiceName())){
            orderItems.setServiceName(payload.getServiceName());
        }
                orderItemRepo.save(orderItems);

        return new ApiResponse<>(MessageUtil.SUCCESS,ResponseCode.SUCCESS,orderItems);



    }

    @Override
    public ApiResponse deleteOrderItem(Long itemId) {
        Optional<OrderItems> orderItemsOptional = orderItemRepo.findById(itemId);
        orderItemRepo.deleteById(itemId);
        return new ApiResponse<>(MessageUtil.DELETE_SUCCESSFUL,ResponseCode.SUCCESS,"Record deleted");
    }

    @Override
    public ApiResponse findByCustomer(String customerName) {
       List<OrderItems> orderItemsList = orderItemRepo.listOrderItemsByCustomerName(customerName);
       if(!orderItemsList.isEmpty())
           return new ApiResponse<>(MessageUtil.SUCCESS,ResponseCode.SUCCESS,orderItemsList);
       else
           return new ApiResponse<>(MessageUtil.INVALID_NAME,ResponseCode.NOT_FOUND,"customer with the name " + customerName.toUpperCase() + " is unavailable");
    }
}
