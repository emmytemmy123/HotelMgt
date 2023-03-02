package fcmb.com.good.services.transaction;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.transactionRequest.OrderItemRequest;
import fcmb.com.good.model.dto.request.transactionRequest.OrdersRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.entity.products.Product;
import fcmb.com.good.model.entity.transaction.OrderItems;
import fcmb.com.good.model.entity.transaction.Orders;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.model.entity.user.Customer;
import fcmb.com.good.repo.products.ProductRepository;
import fcmb.com.good.repo.transaction.OrderItemsRepository;
import fcmb.com.good.repo.transaction.OrdersRepository;
import fcmb.com.good.repo.user.CustomerRepository;
import fcmb.com.good.repo.user.UserRepository;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrdersRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;


    @Override
    public ApiResponse<String> addOrder(OrdersRequest request, OrderItemRequest orderItemRequest) {

        Product existingProduct  = productRepository.findByUuid(request.getProductId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        OrderItems existingOrderItems  = orderItemsRepository.findByUuid(request.getOrderItemsId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Customer existingCustomer  = customerRepository.findByUuid(request.getCustomerId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));


        Orders orders = new Orders();

        orders.setOrderBy(existingCustomer.getName());
        orders.setOrderNo(request.getOrderNo());
        orders.setAmount(existingOrderItems.getAmount());
        orders.setAmountDue(existingOrderItems.getAmount());
        orders.setOrderStatus(request.getOrderStatus());
        orders.setStartTime(request.getStartTime());
        orders.setEndTime(request.getEndTime());

        List<OrderItems> orderItemsList = new ArrayList<>();

        OrderItems orderItems = new OrderItems();
        orderItems.setQuantity(orderItemRequest.getQuantity());
        orderItems.setServiceName(orderItemRequest.getServiceName());
        orderItems.setProduct(existingProduct);

        orderItemsRepository.save(orderItems);
        orderItemsList.add(orderItems);

//      orders.setOrderItemsList(orderItemsList);
        orderRepository.save(orders);


        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record created successfully");
    }



    @Override
    public ApiResponse<String> updateOrder(UUID orderId, OrdersRequest request) {
        return null;
    }



    @Override
    public ApiResponse<String> deleteOrder(UUID orderId) {
        return null;
    }




}
