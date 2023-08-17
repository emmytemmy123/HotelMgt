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



                                //FIND_LISTS_OF_TRANSACTIONS
//    @GetMapping(FIND_ACCOUNT_CATEGORY)
//    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
//    @ApiOperation(value = "Endpoint for retrieving lists of accountCategory", response = AccountCategoryResponse.class, responseContainer = "List")
//    public ApiResponse<List<AccountCategoryResponse>> getListOfAccountCategory(@RequestParam(value = PAGE, defaultValue = PAGE_DEFAULT) int page,
//                                                                               @RequestParam(value = SIZE, defaultValue = SIZE_DEFAULT) int size) {
//        return accountCategoryService.getListOfAccountCategory(page, size);
//    }
//
//    @GetMapping(FIND_ACCOUNT_CHART)
//    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
//    @ApiOperation(value = "Endpoint for retrieving lists of accountChart", response = AccountChartResponse.class, responseContainer = "List")
//    public ApiResponse<List<AccountChartResponse>> getListOfAccountChart(@RequestParam(value = PAGE, defaultValue = PAGE_DEFAULT) int page,
//                                                                         @RequestParam(value = SIZE, defaultValue = SIZE_DEFAULT) int size) {
//        return accountChartService.getListOfAccountChart(page, size);
//    }
//
//    @GetMapping(FIND_EXPENSE_CATEGORY)
//    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
//    @ApiOperation(value = "Endpoint for retrieving lists of expenseCategory", response = ExpenseCategoryResponse.class, responseContainer = "List")
//    public ApiResponse<List<ExpenseCategoryResponse>> getListOfExpenseCategory(@RequestParam(value = PAGE, defaultValue = PAGE_DEFAULT) int page,
//                                                                               @RequestParam(value = SIZE, defaultValue = SIZE_DEFAULT) int size) {
//        return expenseCategoryService.getListOfExpenseCategory(page, size);
//    }
//
//
//    @GetMapping(FIND_MAINTENANCE_CATEGORY)
//    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')  ")
//    @ApiOperation(value = "Endpoint for retrieving lists of maintenanceCategory", response = MaintenanceCategoryResponse.class, responseContainer = "List")
//    public ApiResponse<List<MaintenanceCategoryResponse>> getListOfExpenseRequest(@RequestParam(value = PAGE, defaultValue = PAGE_DEFAULT) int page,
//                                                                             @RequestParam(value = SIZE, defaultValue = SIZE_DEFAULT) int size) {
//        return maintenanceCategoryService.getListOfMaintenanceCategory(page, size);
//    }
//
//    @GetMapping(FIND_EXPENSES)
//    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
//    @ApiOperation(value = "Endpoint for retrieving lists of expenses", response = ExpenseResponse.class, responseContainer = "List")
//    public ApiResponse<List<ExpenseResponse>> getListOfExpense(@RequestParam(value = PAGE, defaultValue = PAGE_DEFAULT) int page,
//                                                               @RequestParam(value = SIZE, defaultValue = SIZE_DEFAULT) int size) {
//        return expenseService.getListOfExpense(page, size);
//    }
//
//    @GetMapping(FIND_MAINTENANCE)
//    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
//    @ApiOperation(value = "Endpoint for retrieving lists of maintenanceRequest", response = MaintenanceResponse.class, responseContainer = "List")
//    public ApiResponse<List<MaintenanceResponse>> getListOfMaintenanceRequest(@RequestParam(value = PAGE, defaultValue = PAGE_DEFAULT) int page,
//                                                                              @RequestParam(value = SIZE, defaultValue = SIZE_DEFAULT) int size) {
//        return maintenanceService.getListOfMaintenance(page, size);
//    }

//    @GetMapping(FIND_PAYMENT_CATEGORY)
//    @ApiOperation(value = "Endpoint for retrieving lists of paymentCategory", response = PaymentCategoryResponse.class, responseContainer = "List")
//    public ApiResponse<List<PaymentCategoryResponse>> getListOfPaymentCategory(@RequestParam(value = PAGE, defaultValue = PAGE_DEFAULT) int page,
//                                                                               @RequestParam(value = SIZE, defaultValue = SIZE_DEFAULT) int size) {
//        return paymentCategoryService.getListOfPaymentCategory(page, size);
//    }

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
//    @PostMapping(ADD_ACCOUNT_CATEGORY)
//    @ApiOperation(value = "Endpoint for adding new accountCategory to database", response = String.class)
//    public ApiResponse<String> addAccountCategory(@Valid @RequestBody AccountCategoryRequest request) {
//        return accountCategoryService.addAccountCategory(request);
//    }
//
//    @PostMapping(ADD_ACCOUNT_CHART)
//    @ApiOperation(value = "Endpoint for adding new accountChart to database", response = String.class)
//    public ApiResponse<String> addAccountChart(@Valid @RequestBody AccountChartRequest request) {
//        return accountChartService.addAccountChart(request);
//    }
//
//    @PostMapping(ADD_EXPENSE_CATEGORY)
//    @ApiOperation(value = "Endpoint for adding new ExpenseCategory to database", response = String.class)
//    public ApiResponse<String> addExpenseCategory(@Valid @RequestBody ExpenseCategoryRequest request) {
//        return expenseCategoryService.addExpenseCategory(request);
//    }
//
//
//    @PostMapping(ADD_MAINTENANCE_CATEGORY)
//    @ApiOperation(value = "Endpoint for adding new maintenanceCategory to database", response = String.class)
//    public ApiResponse<String> addMaintenanceCategory(@Valid @RequestBody MaintenanceCategoryRequest request) {
//        return maintenanceCategoryService.addMaintenanceCategory(request);
//    }
//
//    @PostMapping(ADD_EXPENSES)
//    @ApiOperation(value = "Endpoint for adding new expenses to database", response = String.class)
//    public ApiResponse<String> addExpense(@Valid @RequestBody ExpenseRequest request) {
//        return expenseService.addExpense(request);
//    }
//
//    @PostMapping(ADD_MAINTENANCE)
//    @ApiOperation(value = "Endpoint for adding new maintenanceRequest to database", response = String.class)
//    public ApiResponse<String> addMaintenanceRequest(@Valid @RequestBody MaintenanceRequest request) {
//        return maintenanceService.addMaintenance(request);
//    }

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


                                            //FIND_TRANSACTIONS_BY_ID
//    @GetMapping(FIND_ACCOUNT_CATEGORY_BY_ID)
//    @ApiOperation(value = "Endpoint for fetching accountCategory by id from database", response = AccountCategoryResponse.class)
//    public ApiResponse<AccountCategoryResponse> getAccountCategoryById(@PathVariable(value = "id") UUID accountCategory_id) {
//        return accountCategoryService.getAccountCategoryById(accountCategory_id);
//    }
//
//    @GetMapping(FIND_ACCOUNT_CHART_BY_ID)
//    @ApiOperation(value = "Endpoint for fetching accountChart by id from database", response = AccountChartResponse.class)
//    public ApiResponse<AccountChartResponse> getAccountChartById(@PathVariable(value = "id") UUID accountChart_id) {
//        return accountChartService.getAccountChartById(accountChart_id);
//    }
//
//    @GetMapping(FIND_EXPENSE_CATEGORY_BY_ID)
//    @ApiOperation(value = "Endpoint for fetching ExpenseCategory by id from database", response = ExpenseCategoryResponse.class)
//    public ApiResponse<ExpenseCategoryResponse> getExpenseCategoryById(@PathVariable(value = "id") UUID expenseCategoryId) {
//        return expenseCategoryService.getExpenseCategoryById(expenseCategoryId);
//    }
//
//
//    @GetMapping(FIND_MAINTENANCE_CATEGORY_BY_ID)
//    @ApiOperation(value = "Endpoint for fetching maintenanceCategory by id from database", response = MaintenanceCategoryResponse.class)
//    public ApiResponse<MaintenanceCategoryResponse> getMaintenanceCategoryById(@PathVariable(value = "id") UUID maintenanceCategoryId) {
//        return maintenanceCategoryService.getMaintenanceCategoryById(maintenanceCategoryId);
//    }
//
//    @GetMapping(FIND_EXPENSES_BY_ID)
//    @ApiOperation(value = "Endpoint for fetching expenses by id from database", response = ExpenseResponse.class)
//    public ApiResponse<ExpenseResponse> getExpenseById(@PathVariable(value = "id") UUID expense_id) {
//        return expenseService.getExpenseById(expense_id);
//    }
//
//    @GetMapping(FIND_MAINTENANCE_BY_ID)
//    @ApiOperation(value = "Endpoint for fetching maintenanceRequest by id from database", response = MaintenanceResponse.class)
//    public ApiResponse<MaintenanceResponse> getMaintenanceRequestById(@PathVariable(value = "id") UUID maintenance_id) {
//        return maintenanceService.getMaintenanceById(maintenance_id);
//    }

//    @GetMapping(FIND_PAYMENT_BY_ID)
//    @ApiOperation(value = "Endpoint for fetching payment by id from database", response = PaymentResponse.class)
//    public ApiResponse<PaymentResponse> getPaymentById(@PathVariable(value = "id") UUID paymentId) {
//        return paymentService.getPaymentById(paymentId);
//    }


    @GetMapping(FIND_ORDER_BY_ID)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for fetching Orders by id from database", response = OrdersResponse.class)
    public ApiResponse<OrdersResponse> getPaymentOrderById(@RequestParam UUID orderId) {
        return ordersService.getOrderById(orderId);
    }


                                            //UPDATE_TRANSACTION
//    @PutMapping(UPDATE_ACCOUNT_CATEGORY)
//    @ApiOperation(value = "Endpoint for updating accountCategory by id from database", response = String.class)
//    public ApiResponse<String> updateAccountCategory(@PathVariable(value = "id") UUID accountCategory_id,
//                                                     @RequestBody AccountCategoryRequest request) {
//        return accountCategoryService.updateAccountCategory(accountCategory_id, request);
//    }
//
//    @PutMapping(UPDATE_ACCOUNT_CHART)
//    @ApiOperation(value = "Endpoint for updating accountChart by id from database", response = String.class)
//    public ApiResponse<String> updateAccountChart(@PathVariable(value = "id") UUID accountChart_id,
//                                                  @RequestBody AccountChartRequest request) {
//        return accountChartService.updateAccountChart(accountChart_id, request);
//    }
//
//    @PutMapping(UPDATE_EXPENSE_CATEGORY)
//    @ApiOperation(value = "Endpoint for updating ExpenseCategory by id from database", response = String.class)
//    public ApiResponse<String> updateExpenseCategory(@PathVariable(value = "id") UUID expenseCategoryId,
//                                                     @RequestBody ExpenseCategoryRequest request) {
//        return expenseCategoryService.updateExpenseCategory(expenseCategoryId, request);
//    }
//
//
//    @PutMapping(UPDATE_MAINTENANCE_CATEGORY)
//    @ApiOperation(value = "Endpoint for updating maintenanceCategory by id from database", response = String.class)
//    public ApiResponse<String> updateMaintenanceCategory(@PathVariable(value = "id") UUID maintenanceCategoryId,
//                                                    @RequestBody MaintenanceCategoryRequest request) {
//        return maintenanceCategoryService.updateMaintenanceCategory(maintenanceCategoryId, request);
//    }
//
//    @PutMapping(UPDATE_EXPENSES)
//    @ApiOperation(value = "Endpoint for updating expenses by id from database", response = String.class)
//    public ApiResponse<String> updateExpense(@PathVariable(value = "id") UUID expenses_id,
//                                             @RequestBody ExpenseRequest request) {
//        return expenseService.updateExpense(expenses_id, request);
//    }
//
//    @PutMapping(UPDATE_MAINTENANCE)
//    @ApiOperation(value = "Endpoint for updating maintenanceRequest by id from database", response = String.class)
//    public ApiResponse<String> updateMaintenanceRequest(@PathVariable(value = "id") UUID maintenanceRequest_id,
//                                                        @RequestBody MaintenanceRequest request) {
//        return maintenanceService.updateMaintenance(maintenanceRequest_id, request);
//    }



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




                                            //DELETE_TRANSACTIONS
//    @DeleteMapping(DELETE_ACCOUNT_CATEGORY)
//    @ApiOperation(value = "Endpoint for deleting accountCategory by id from database", response = String.class)
//    public ApiResponse<String> deleteAccountCategory(@PathVariable(value = "id") UUID accountCategory_id) {
//        return accountCategoryService.deleteAccountCategory(accountCategory_id);
//    }
//
//    @DeleteMapping(DELETE_ACCOUNT_CHART)
//    @ApiOperation(value = "Endpoint for deleting accountChart by id from database", response = String.class)
//    public ApiResponse<String> deleteAccountChart(@PathVariable(value = "id") UUID accountChart_id) {
//        return accountChartService.deleteAccountChart(accountChart_id);
//    }
//
//    @DeleteMapping(DELETE_EXPENSE_CATEGORY)
//    @ApiOperation(value = "Endpoint for deleting ExpenseCategory by id from database", response = String.class)
//    public ApiResponse<String> deleteExpenseCategory(@PathVariable(value = "id") UUID expenseCategoryId) {
//        return expenseCategoryService.deleteExpenseCategory(expenseCategoryId);
//    }
//
//
//    @DeleteMapping(DELETE_MAINTENANCE_CATEGORY)
//    @ApiOperation(value = "Endpoint for deleting maintenanceCategory by id from database", response = String.class)
//    public ApiResponse<String> deleteMaintenanceCategory(@PathVariable(value = "id") UUID maintenanceCategoryId) {
//        return maintenanceCategoryService.deleteMaintenanceCategory(maintenanceCategoryId);
//    }
//
//    @DeleteMapping(DELETE_EXPENSES)
//    @ApiOperation(value = "Endpoint for deleting expenses by id from database", response = String.class)
//    public ApiResponse<String> deleteExpense(@PathVariable(value = "id") UUID expense_id) {
//        return expenseService.deleteExpense(expense_id);
//
//    }
//
//    @DeleteMapping(DELETE_MAINTENANCE)
//    @ApiOperation(value = "Endpoint for deleting maintenanceRequest by id from database", response = String.class)
//    public ApiResponse<String> deleteUser(@PathVariable(value = "id") UUID maintenance_id) {
//        return maintenanceService.deleteMaintenance(maintenance_id);
//    }
//
//
//    @DeleteMapping(DELETE_PAYMENT_CATEGORY)
//    @ApiOperation(value = "Endpoint for deleting paymentCategory by id from database", response = String.class)
//    public ApiResponse<String> deletePaymentCategory(@PathVariable(value = "id") UUID paymentCategory_id) {
//        return paymentCategoryService.deletePaymentCategory(paymentCategory_id);
//    }


                                    //FIND_LISTS_OF_EXPENSES_BY_NAME

//    @GetMapping(FIND_EXPENSES_BY_NAME_AND_CATEGORY)
//    @ApiOperation(value = "Endpoint for retrieving lists of expenses by name and category", response = ExpenseResponse.class, responseContainer = "List")
//    public ApiResponse<List<ExpenseResponse>> getListOfExpense(@RequestParam(value = PAGE, defaultValue = PAGE_DEFAULT) int page,
//                                                               @RequestParam(value = SIZE, defaultValue = SIZE_DEFAULT) int size,
//                                                               @RequestParam String name) {
//        return expenseService.searchExpenseByNameAndCategory(name);
//    }


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