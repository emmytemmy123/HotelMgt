package fcmb.com.good.services.transaction;

import fcmb.com.good.model.dto.request.transactionRequest.PaymentRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.transactionResponse.PaymentResponse;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public interface PaymentService {

    ApiResponse<List<PaymentResponse>> getListOfPayment(int page, int size);

    ApiResponse<List<PaymentResponse>> findListOfPaymentByDateRange( String from, String to);

    ApiResponse<String> addPayment(PaymentRequest request);

//    ApiResponse<PaymentResponse> getPaymentById(UUID paymentId);

    ApiResponse<List<PaymentResponse>> getPaymentByOrderId(UUID orderId);

    ApiResponse<List<PaymentResponse>> findPaymentByDate(String dateCreated);

    ApiResponse<List<PaymentResponse>> findPaymentBySalesPerson(UUID userId);

    ApiResponse<List<PaymentResponse>> findPaymentByCustomer(UUID customerId);




}
