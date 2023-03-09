package fcmb.com.good.services.transaction;

import fcmb.com.good.model.dto.request.transactionRequest.PaymentRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.transactionResponse.PaymentResponse;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface PaymentService {


    ApiResponse<List<PaymentResponse>> getListOfPayment(int page, int size);

    ApiResponse<List<PaymentResponse>> findListOfPaymentByDateRange(int page, int size, String from, String to);

    ApiResponse<String> addPayment(PaymentRequest request);

    ApiResponse<PaymentResponse> getPaymentById(UUID paymentId);

    ApiResponse<PaymentResponse> getPaymentByOrderId(UUID orderId);

    ApiResponse<String> updatePayment( UUID paymentId, PaymentRequest request);

    ApiResponse<String> deletePayment(UUID paymentId);

    ApiResponse<List<PaymentResponse>> findPaymentByDate(Date dateCreated);

    ApiResponse<List<PaymentResponse>> findPaymentBySalesPerson(UUID userId);

    ApiResponse<List<PaymentResponse>> findPaymentByCustomer(UUID customerId);




}
