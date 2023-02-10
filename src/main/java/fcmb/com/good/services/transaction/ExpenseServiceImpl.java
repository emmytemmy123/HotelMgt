package fcmb.com.good.services.transaction;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.transactionRequest.ExpenseRequest;
import fcmb.com.good.model.dto.response.transactionResponse.ExpenseResponse;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.entity.transaction.Expenses;
import fcmb.com.good.repo.transaction.ExpensesRepository;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {
    private  final ExpensesRepository expensesRepository;

    @Override
    public ApiResponse<List<ExpenseResponse>> getListOfExpense(int page, int size) {
        List<Expenses> expensesList = expensesRepository.findAll(PageRequest.of(page,size)).toList();
        if(expensesList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(expensesList, ExpenseResponse.class));
    }

    @Override
    public ApiResponse<String> addExpense(@RequestBody ExpenseRequest request) {
        Expenses expenses = Mapper.convertObject(request,Expenses.class);
        expensesRepository.save(expenses);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record added Successfully");
    }

    @Override
    public ApiResponse<ExpenseResponse> getExpenseById(@RequestParam("id") UUID expenseId) {
        Optional<Expenses> expenses = expensesRepository.findByUuid(expenseId);

        if(expenses.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        Expenses cm = expenses.get();
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(cm,ExpenseResponse.class));

    }

    private Expenses validateExpenses(UUID uuid){
        Optional<Expenses> expenses = expensesRepository.findByUuid(uuid);
        if(expenses.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return expenses.get();
    }

    @Override
    public ApiResponse<String> updateExpense(UUID expenseId, @RequestBody ExpenseRequest request) {
        Expenses expenses = validateExpenses(expenseId);
        expenses.setExpense_type(request.getExpense_type());
        expenses.setDescription(request.getDescription());

        expensesRepository.save(expenses);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Updated Successfully");
    }

    @Override
    public ApiResponse<String> deleteExpense(UUID expenseId) {
        Expenses expenses = validateExpenses(expenseId);
        expensesRepository.delete(expenses);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }


}
