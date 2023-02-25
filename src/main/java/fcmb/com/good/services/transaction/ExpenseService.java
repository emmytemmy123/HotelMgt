package fcmb.com.good.services.transaction;

import fcmb.com.good.model.dto.request.transactionRequest.ExpenseRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.transactionResponse.ExpenseResponse;
import fcmb.com.good.model.entity.transaction.Expenses;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {


    ApiResponse<List<ExpenseResponse>> getListOfExpense(int page, int size);

    ApiResponse<String> addExpense(ExpenseRequest request);

    ApiResponse<ExpenseResponse> getExpenseById(UUID expenseId);

    ApiResponse<String> updateExpense( UUID expenseId, ExpenseRequest request);

    ApiResponse<String>deleteExpense(UUID expenseId);

    ApiResponse<List<ExpenseResponse>> searchExpenseByNameAndCategory(String query);



}
