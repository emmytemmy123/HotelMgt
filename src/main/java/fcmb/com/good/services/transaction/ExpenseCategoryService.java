package fcmb.com.good.services.transaction;

import fcmb.com.good.model.dto.request.transactionRequest.ExpenseCategoryRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.transactionResponse.ExpenseCategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface ExpenseCategoryService {

    ApiResponse<List<ExpenseCategoryResponse>> getListOfExpenseCategory(int page, int size);

    ApiResponse<String> addExpenseCategory(ExpenseCategoryRequest request);

    ApiResponse<ExpenseCategoryResponse> getExpenseCategoryById(UUID expenseCategoryId);

    ApiResponse<String> updateExpenseCategory( UUID expenseCategoryId, ExpenseCategoryRequest request);

    ApiResponse<String> deleteExpenseCategory(UUID expenseCategoryId);




}
