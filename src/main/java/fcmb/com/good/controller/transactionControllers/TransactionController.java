package fcmb.com.good.controller.transactionControllers;


import fcmb.com.good.model.dto.request.orderItemRequest.OrderItemsRequest;
import fcmb.com.good.model.dto.request.transactionRequest.*;
import fcmb.com.good.model.dto.request.transactionRequest.ExpenseRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.transactionResponse.*;
import fcmb.com.good.services.transaction.*;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static fcmb.com.good.utills.EndPoints.TransactionEndPoints.*;
import static fcmb.com.good.utills.EndpointParam.*;
import static fcmb.com.good.utills.EndpointParam.SIZE_DEFAULT;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(transaction)
@RequiredArgsConstructor
public class TransactionController {

    private final PaymentService paymentService;
    private final OrderService ordersService;
    private final MaintenanceCategoryService maintenanceCategoryService;
    private final MaintenanceRequestRequestServiceImpl maintenanceRequestRequestService;



                                //FIND_LISTS_OF_TRANSACTIONS


    @GetMapping(FIND_PAYMENT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') ")
    @ApiOperation(value = "Endpoint for retrieving lists of payment", response = PaymentResponse.class, responseContainer = "List")
    public ApiResponse<List<PaymentResponse>> getListOfPayment(@RequestParam(value = PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                               @RequestParam(value = SIZE, defaultValue = SIZE_DEFAULT) int size) {
        return paymentService.getListOfPayment(page, size);
    }

    @GetMapping(FIND_ORDER)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for retrieving lists of orders", response = OrdersResponse.class, responseContainer = "List")
    public ApiResponse<List<OrdersResponse>> getListOfOrders(@RequestParam(value = PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                               @RequestParam(value = SIZE, defaultValue = SIZE_DEFAULT) int size) {
        return ordersService.getListOfOrder(page, size);
    }




                                             //ADD_TRANSACTIONS

    @PostMapping(ADD_PAYMENT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER') ")
    @ApiOperation(value = "Endpoint for adding new payment to database", response = String.class)
    public ApiResponse<String> addPayment(@Valid @RequestBody PaymentRequest request) {
        return paymentService.addPayment(request);
    }


    @PostMapping(ADD_ORDER)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER') ")
    @ApiOperation(value = "Endpoint for adding new order to database", response = String.class)
    public ApiResponse<String> addOrderItem(@Valid @RequestBody OrdersRequest request) {
        return ordersService.addOrder(request);
    }

    @PostMapping(ADD_MAINTENANCE_CATEGORY)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for adding new maintenanceCategory to database", response = String.class)
    public ApiResponse<String> addMaintenanceCategory(@Valid @RequestBody MaintenanceCategoryRequest request) {
        return maintenanceCategoryService.addMaintenanceCategory(request);
    }

    @PostMapping(ADD_MAINTENANCE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for adding new maintenanceRequest to database", response = String.class)
    public ApiResponse<String> addMaintenanceRequest(@Valid @RequestBody MaintenanceRequest request) {
        return maintenanceRequestRequestService.addMaintenance(request);
    }


                                            //FIND_TRANSACTIONS_BY_ID


    @GetMapping(FIND_ORDER_BY_ID)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for fetching Orders by id from database", response = OrdersResponse.class)
    public ApiResponse<OrdersResponse> getPaymentOrderById(@RequestParam UUID orderId) {
        return ordersService.getOrderById(orderId);
    }


                                            //UPDATE_TRANSACTION


    @PutMapping(UPDATE_ORDER)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER') ")
    @ApiOperation(value = "Endpoint for updating ORDER by id from database", response = String.class)
    public ApiResponse<String> updateOrder(@PathVariable(value = "id")  UUID orderItemUuid,
                                             @RequestBody OrderItemRequest request) {
        return ordersService.updateOrder(orderItemUuid, request);
    }


    @PutMapping(UPDATE_ORDER_ADD_PRODUCT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER') ")
    @ApiOperation(value = "Endpoint for updating ORDER by id and add product from database", response = String.class)
    public ApiResponse<String> updateOrderAndAddProduct(@PathVariable(value = "id")  UUID orderUuid,
                                           @RequestBody OrdersRequest2 request) {
        return ordersService.addProductToExistingOrder(orderUuid, request);
    }

    @PutMapping(UPDATE_ORDER_TO_CHECK_IN)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER') ")
    @ApiOperation(value = "Endpoint for updating ORDER by id and update checkIn from database", response = String.class)
    public ApiResponse<String> updateOrderAndToCheckIn(@PathVariable(value = "id")  UUID orderUuid,
                                                        @RequestBody OrderRequest3 request) {
        return ordersService.updateOrderToUpdateCheckIn(orderUuid, request);
    }





                                //FIND_LISTS_OF_PAYMENT_BY_DATE
    @GetMapping(FIND_LISTS_OF_PAYMENT_BY_DATE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') ")
    @ApiOperation(value = "Endpoint for retrieving lists of payment by date", response = PaymentResponse.class, responseContainer = "List")
    public ApiResponse<List<PaymentResponse>> getListOfPayment(@RequestParam String dateCreated) {
        return paymentService.findPaymentByDate(dateCreated);
    }

                                    //FIND_LISTS_OF_ORDER_BY_DATE
    @GetMapping(FIND_LISTS_OF_ORDER_BY_DATE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for retrieving lists of order by date", response = OrdersResponse.class, responseContainer = "List")
    public ApiResponse<List<OrdersResponse>> getListOfOrderByDate(@RequestParam String dateCreated) {
        return ordersService.findOrderByDate(dateCreated);
    }


                                    //FIND_PAYMENT_BY_SALES_PERSON
    @GetMapping(FIND_PAYMENT_BY_SALES_PERSON)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for fetching payment by salesPerson from database", response = PaymentResponse.class, responseContainer = "List")
    public ApiResponse<List<PaymentResponse>> getPaymentBySalesPerson(@RequestParam UUID userId) {
        return paymentService.findPaymentBySalesPerson(userId);
    }

                                    //FIND_PAYMENT_BY_CUSTOMER
    @GetMapping(FIND_PAYMENT_BY_CUSTOMER)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for fetching payment by customer from database", response = PaymentResponse.class, responseContainer = "List")
    public ApiResponse<List<PaymentResponse>> getPaymentByCustomer(@RequestParam UUID customerId) {
        return paymentService.findPaymentByCustomer(customerId);
    }

                                        //FIND_ORDER_BY_CUSTOMER
    @GetMapping(FIND_ORDER_BY_CUSTOMER)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for fetching order by customer from database", response = OrdersResponse.class, responseContainer = "List")
    public ApiResponse<List<OrdersResponse>> getOrdersByCustomer(@RequestParam UUID customerUuid) {
        return ordersService.getOrdersByCustomer(customerUuid);
    }


                                    //FIND_LIST_OF_PAYMENT_BY_DATE_RANGE

    @GetMapping(FIND_LIST_OF_PAYMENT_BY_DATE_RANGE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for retrieving lists of payment by dateRange", response = PaymentResponse.class, responseContainer = "List")
    public ApiResponse<List<PaymentResponse>> getListOfPaymentByDateRange(@RequestParam String from, @RequestParam String to) {
        return paymentService.findListOfPaymentByDateRange(from, to);
    }


                                //FIND_LIST_OF_PAYMENT_BY_ORDER_ID

    @GetMapping(FIND_PAYMENT_BY_ORDER_ID)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for fetching payment by orderId from database", response = PaymentResponse.class, responseContainer = "List")
    public ApiResponse<List<PaymentResponse>> getPaymentByOrderId(@RequestParam UUID orderId) {
        return paymentService.getPaymentByOrderId(orderId);
    }




}