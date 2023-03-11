package fcmb.com.good.services.transaction;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.orderItemRequest.OrderItemsRequest;
import fcmb.com.good.model.dto.request.transactionRequest.OrderItemRequest;
import fcmb.com.good.model.dto.request.transactionRequest.OrdersRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.transactionResponse.OrdersResponse;
import fcmb.com.good.model.dto.response.transactionResponse.PaymentResponse;
import fcmb.com.good.model.entity.products.Product;
import fcmb.com.good.model.entity.transaction.OrderItems;
import fcmb.com.good.model.entity.transaction.Orders;
import fcmb.com.good.model.entity.transaction.Payment;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.model.entity.user.Customer;
import fcmb.com.good.repo.products.ProductRepository;
import fcmb.com.good.repo.transaction.OrderItemsRepository;
import fcmb.com.good.repo.transaction.OrdersRepository;
import fcmb.com.good.repo.user.CustomerRepository;
import fcmb.com.good.repo.user.UserRepository;
import fcmb.com.good.utills.EndPoints.OrderUtils;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrdersRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;


    @Override
    /**
     * @Validate and Find the list of  order
     * @Validate if the List of order is empty otherwise return record not found*
     * @return the list of order and a Success Message
     * * */
    public ApiResponse<List<OrdersResponse>> getListOfOrder(int page, int size) {

        List<Orders> ordersList = orderRepository.findAll(PageRequest.of(page,size)).toList();
        if(ordersList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(ordersList, OrdersResponse.class));

    }

    @Override
    /**
     * @Validating and Finding the list of OrderOptional by uuid
     * @Validate if the List of OrderOptional is empty otherwise return record not found
     * Create the Order definition and get the OrderOptional by uuid
     * @return the list of OrderOptional and a Success Message
     * * */
    public ApiResponse<OrdersResponse> getOrderById(UUID orderId) {

        Optional<Orders> ordersOptional = orderRepository.findByUuid(orderId);

        if(ordersOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        Orders orders = ordersOptional.get();

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(orders,OrdersResponse.class));

    }


    @Override
    public ApiResponse<String> addOrder(OrdersRequest request) {

        Customer existingCustomer  = customerRepository.findByUuid(request.getCustomerId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        AppUser existingUser  = userRepository.findByUuid(request.getCreatedById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Orders orders = new Orders();

        orders.setOrderBy(existingCustomer.getName());
        orders.setOrderNo(OrderUtils.generateOrderNumber());
        orders.setOrderStatus("pending");
        orders.setStartTime(LocalDateTime.now());
        orders.setCustomer(existingCustomer);


        Double totalAmount = 0.0;

        for (OrderItemRequest orderItem : request.getItems()) {

            Product existingProduct  = productRepository.findByUuid(orderItem.getProductId())
                    .orElse(null);

            if(existingProduct != null){
                OrderItems orderItems = new OrderItems();

                orderItems.setItemName(existingProduct.getName());
                orderItems.setSalesPrice(existingProduct.getSalesPrice());
                orderItems.setQuantity(orderItem.getQuantity());
                orderItems.setAmount((existingProduct.getSalesPrice() * (orderItem.getQuantity())));
                orderItems.setPurchasePrice(existingProduct.getPurchasePrice());
                orderItems.setPurchaseAmount((existingProduct.getPurchasePrice())*(orderItem.getQuantity()));
                orderItems.setProfit((orderItems.getAmount())-(orderItems.getPurchaseAmount()));
                orderItems.setServiceName(orderItem.getServiceName());
                orderItems.setDescription(orderItem.getDescription());
                orderItems.setTransactionDate(LocalDateTime.now());
                orderItems.setStatus("pending");
                orderItems.setCreatedBy(existingUser);
                orderItems.setProduct(existingProduct);
                orderItems.setOrders(orders);

                totalAmount += orderItem.getQuantity() * existingProduct.getSalesPrice();

                orderItemsRepository.save(orderItems);

            }

            orders.setAmount(totalAmount);
            orders.setAmountDue(totalAmount);
            orderRepository.save(orders);

        }
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record created successfully");
    }



    @Override
    public ApiResponse<List<OrdersResponse>> findOrderByCustomer(UUID customerId) {

        Optional<Orders> ordersOptional = orderRepository.findOrdersByCustomer(customerId);

        if(ordersOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        Orders orders = ordersOptional.get();

        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(orders,OrdersResponse.class));

    }



    @Override
    public ApiResponse<List<OrdersResponse>> findOrderByDate(Date dateCreated) {

        List<Orders> ordersList = orderRepository.findOrderByDateCreated(String.valueOf(dateCreated));

        if(ordersList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(ordersList, OrdersResponse.class));

    }



}
