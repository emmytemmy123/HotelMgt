package fcmb.com.good.services.transaction;


import fcmb.com.good.model.dto.request.transactionRequest.MaintenanceCategoryRequest;
import fcmb.com.good.model.dto.request.transactionRequest.PaymentCategoryRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.transactionResponse.MaintenanceCategoryResponse;
import fcmb.com.good.model.dto.response.transactionResponse.PaymentCategoryResponse;

import java.util.List;
import java.util.UUID;

public interface PaymentCategoryService {

    ApiResponse<List<PaymentCategoryResponse>> getListOfPaymentCategory(int page, int size);

    ApiResponse<String> addPaymentCategory(PaymentCategoryRequest request);

    ApiResponse<PaymentCategoryResponse> getPaymentCategoryById(UUID paymentCategoryId);

    ApiResponse<String> updatePaymentCategory( UUID paymentCategoryId, PaymentCategoryRequest request);

    ApiResponse<String> deletePaymentCategory(UUID paymentCategoryId);

}
