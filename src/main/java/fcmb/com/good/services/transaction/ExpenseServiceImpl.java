package fcmb.com.good.services.transaction;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.transactionRequest.ExpenseRequest;
import fcmb.com.good.model.dto.response.productsResponse.ProductResponse;
import fcmb.com.good.model.dto.response.transactionResponse.ExpenseResponse;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.entity.products.Product;
import fcmb.com.good.model.entity.transaction.AccountCategory;
import fcmb.com.good.model.entity.transaction.AccountChart;
import fcmb.com.good.model.entity.transaction.ExpenseCategory;
import fcmb.com.good.model.entity.transaction.Expenses;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.model.entity.user.Customer;
import fcmb.com.good.model.entity.user.Employee;
import fcmb.com.good.repo.transaction.ExpenseCategoryRepository;
import fcmb.com.good.repo.transaction.ExpensesRepository;
import fcmb.com.good.repo.user.EmployeeRepository;
import fcmb.com.good.repo.user.UserRepository;
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
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;



    @Override
    /**
     * @Validate and Find the list of  Expenses
     * @Validate if the List of Expenses is empty otherwise return record not found*
     * @return the list of Expenses and a Success Message
     * * */
    public ApiResponse<List<ExpenseResponse>> getListOfExpense(int page, int size) {
        List<Expenses> expensesList = expensesRepository.findAll(PageRequest.of(page,size)).toList();

        if(expensesList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(expensesList, ExpenseResponse.class));
    }


    /**
     * @Validating existingExpenseOptional by name
     * @Validating existingExpenseOptional by name
     * @Validate the List of existingExpense and existingExpenseOptional is present otherwise return Duplicate Record*
     * * */
    private void validateDuplicateExpenses(String name){

        Optional<Expenses> expensesOptional = expensesRepository.findByName(name);

        if(expensesOptional.isPresent() )
            throw new RecordNotFoundException("Duplicate record");
    }


    @Override
    /**
     * @Validate that no duplicate Expenses is allowed
     * @Validate that Expenses exists otherwise return record not found
     * Create the Expenses definition and save
     * @return success message
     * * */
    public ApiResponse<String> addExpense(ExpenseRequest request) {

        validateDuplicateExpenses(request.getName());

        AppUser existingUser  = userRepository.findByUuid(request.getCreatedById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Employee existingEmployee  = employeeRepository.findByUuid(request.getCurrentEmployeeId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        ExpenseCategory existingExpenseCategory  = expenseCategoryRepository.findByUuid(request.getCurrentExpenseCategoryId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Expenses expenses = new Expenses();

        expenses.setCategory(request.getCategory());
        expenses.setName(request.getName());
        expenses.setDescription(request.getDescription());
        expenses.setPrice(request.getPrice());
        expenses.setQuantity(request.getQuantity());
        expenses.setTotalAmount((request.getPrice())*(request.getQuantity()));
        expenses.setExpensedBy(existingEmployee.getName());
        expenses.setExpenseCategory(existingExpenseCategory);
        expenses.setCreatedBy(existingUser);
        expenses.setExpenseId(existingEmployee);

        expensesRepository.save(expenses);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record created successfully");

    }


    @Override
    /**
     * @Validating and Finding the list of ExpensesOptional by uuid
     * @Validate if the List of ExpensesOptional is empty otherwise return record not found
     * Create the Expenses definition and get the ExpensesOptional by uuid
     * @return the list of Expenses and a Success Message
     * * */
    public ApiResponse<ExpenseResponse> getExpenseById( UUID expenseId) {
        Optional<Expenses> expensesOptional = expensesRepository.findByUuid(expenseId);

        if(expensesOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        Expenses expenses = expensesOptional.get();

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(expenses,ExpenseResponse.class));

    }


    /**
     * @validating ExpensesOptional by uuid
     * @Validate if the List of Expenses is empty otherwise return record not found
     * @return ExpensesOptional
     * * */
    private Expenses validateExpenses(UUID uuid){
        Optional<Expenses> expensesOptional = expensesRepository.findByUuid(uuid);
        if(expensesOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return expensesOptional.get();
    }

    @Override
    /**
     * @validating ExpensesOptional by uuid
     * @Validate if the List of ExpensesOptional is empty otherwise return record not found
     * Create the ExpensesOptional definition and update
     * @return a Success Message
     * * */
    public ApiResponse<String> updateExpense(UUID expenseId, ExpenseRequest request) {

        Expenses expenses = validateExpenses(expenseId);

        expenses.setCategory(request.getCategory());
        expenses.setName(request.getName());
        expenses.setDescription(request.getDescription());
        expenses.setPrice(request.getPrice());
        expenses.setQuantity(request.getQuantity());

        expensesRepository.save(expenses);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Updated Successfully");
    }

    @Override
    /**
     * @validate Expenses by uuid
     * @Validate if Expenses is empty otherwise return record not found
     * @Delete Expenses
     * @return a Success Message
     * * */
    public ApiResponse<String> deleteExpense(UUID expenseId) {
        Expenses expenses = validateExpenses(expenseId);
        expensesRepository.delete(expenses);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }

    @Override
    /**
     * @Search the list of all expenses by name
     * @Validate if the List of expenses is empty otherwise return record not found
     * @return the list of expenses by name
     * * */
    public ApiResponse<List<ExpenseResponse>> searchExpenseByNameAndCategory(String name) {

        List<Expenses> searchExpensesByName = expensesRepository.searchExpenseByNameAndCategory(name);

        if(searchExpensesByName.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(searchExpensesByName, ExpenseResponse.class));

    }


}
