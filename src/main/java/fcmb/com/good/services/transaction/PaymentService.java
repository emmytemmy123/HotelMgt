package fcmb.com.good.services.transaction;

import fcmb.com.good.model.dto.request.transactionRequest.PaymentRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.transactionResponse.PaymentResponse;
import fcmb.com.good.model.entity.transaction.Payment;

import java.util.List;
import java.util.UUID;

public interface PaymentService {


    ApiResponse<List<PaymentResponse>> getListOfPayment(int page, int size);

    ApiResponse<String> addPayment(PaymentRequest request);

    ApiResponse<PaymentResponse> getPaymentById(UUID paymentId);

    ApiResponse<String> updatePayment( UUID paymentId, PaymentRequest request);

    ApiResponse<String> deletePayment(UUID paymentId);




}
