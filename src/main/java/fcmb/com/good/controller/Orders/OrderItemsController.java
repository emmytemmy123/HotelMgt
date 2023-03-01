package fcmb.com.good.controller.Orders;

import fcmb.com.good.dto.ApiResponse;
import fcmb.com.good.model.dto.request.orderItemRequest.OrderItemsRequest;
import fcmb.com.good.model.dto.response.transactionResponse.AccountCategoryResponse;
import fcmb.com.good.services.transaction.OrderItemService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static fcmb.com.good.utills.EndPoints.TransactionEndPoints.*;

@RestController
@RequestMapping(ORDER_ITEM)
@RequiredArgsConstructor
public class OrderItemsController {
    private final OrderItemService orderItemService;

    @PostMapping(ADD_ORDER_ITEMS)
    @ApiOperation(value = "endpoint for adding order items", response = ApiResponse.class)
    public ApiResponse addOrderItems(@RequestBody OrderItemsRequest payload){
        return orderItemService.addOrderItem(payload);
    }
    @PostMapping(UPDATE_ORDER_ITEMS)
    @ApiOperation(value = "endpoint for updating order items", response = ApiResponse.class)
    public ApiResponse updateOrderItems( @RequestParam("id") Long Id,@RequestBody OrderItemsRequest payload){
        return orderItemService.updateOrderItem(Id,payload);
    }
    @PostMapping(DELETE_ORDER_ITEMS)
    @ApiOperation(value = "endpoint for deleting order items", response = ApiResponse.class)
    public ApiResponse deleteOrderItems( @RequestParam("id") Long Id){
        return orderItemService.deleteOrderItem(Id);
    }
    @PostMapping(LIST_BY_CUSTOMER_NAME)
    @ApiOperation(value = "endpoint for fetching order items by customer's name", response = ApiResponse.class)
    public ApiResponse listOrderItemsByCustomer( @RequestParam("customerName") String name){
        return orderItemService.findByCustomer(name);
    }

}
