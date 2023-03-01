package fcmb.com.good.services.transaction;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.transactionRequest.ExpenseCategoryRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.transactionResponse.ExpenseCategoryResponse;
import fcmb.com.good.model.entity.transaction.ExpenseCategory;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.repo.transaction.ExpenseCategoryRepository;
import fcmb.com.good.repo.transaction.ExpensesRepository;
import fcmb.com.good.repo.user.UserRepository;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseCategoryImpl implements ExpenseCategoryService{

    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final ExpensesRepository expensesRepository;
    private final UserRepository userRepository;

    @Override
    /**
     * @Validate and Find the list of  expenseCategory
     * @Validate if the List of expenseCategory is empty otherwise return record not found*
     * @return the list of expenseCategory and a Success Message
     * * */
    public ApiResponse<List<ExpenseCategoryResponse>> getListOfExpenseCategory(int page, int size) {

        List<ExpenseCategory> expenseCategoryList = expenseCategoryRepository.findAll(PageRequest.of(page,size)).toList();
        if(expenseCategoryList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(expenseCategoryList, ExpenseCategoryResponse.class));

    }



    @Override
    /**
     * @Validate that no duplicate expenseCategory is allowed
     * @Validate that expenseCategory exists otherwise return record not found
     * Create the expenseCategory definition and save
     * @return success message
     * * */
    public ApiResponse<String> addExpenseCategory(ExpenseCategoryRequest request) {

        Optional<ExpenseCategory> expenseCategoryOptional = validateDuplicateExpenseCategory(request.getName());

        AppUser existingUser  = userRepository.findByUuid(request.getCreatedById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        if (!expenseCategoryOptional.isEmpty()) {
            return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
                    "Duplicate Record");
        }

        ExpenseCategory expenseCategory = new ExpenseCategory();

        expenseCategory.setName(request.getName());
        expenseCategory.setDescription(request.getDescription());
        expenseCategory.setPostedBy(existingUser.getName());
        expenseCategory.setCreatedBy(existingUser);

        expenseCategoryRepository.save(expenseCategory);

        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Added successfully");

    }


    /**
     * @Validating existingExpenseCategoryOption by category
     * @Validate if the List of existingExpenseCategoryOption is empty otherwise return Duplicate Record
     * */
    private Optional<ExpenseCategory> validateDuplicateExpenseCategory(String name) {
        Optional<ExpenseCategory> existingExpenseCategoryOption = expenseCategoryRepository.findByName(name);
        return existingExpenseCategoryOption;
    }

    @Override
    /**
     * @Validating and Finding the list of expenseCategoryOptional by uuid
     * @Validate if the List of expenseCategoryOptional is empty otherwise return record not found
     * Create the expenseCategory definition and get the expenseCategoryOptional by uuid
     * @return the list of expenseCategory and a Success Message
     * * */
    public ApiResponse<ExpenseCategoryResponse> getExpenseCategoryById(UUID expenseCategoryId) {

        Optional<ExpenseCategory> expenseCategoryOptional = expenseCategoryRepository.findByUuid(expenseCategoryId);

        if(expenseCategoryOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        ExpenseCategory expenseCategory = expenseCategoryOptional.get();

        return new ApiResponse<ExpenseCategoryResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(expenseCategory,ExpenseCategoryResponse.class));

    }


    /**
     * @validating expenseCategoryOptional by uuid
     * @Validate if the List of expenseCategory is empty otherwise return record not found
     * @return expenseCategoryOptional
     * * */
    private ExpenseCategory validateExpenseCategory(UUID uuid){
        Optional<ExpenseCategory> expenseCategoryOptional = expenseCategoryRepository.findByUuid(uuid);
        if(expenseCategoryOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return expenseCategoryOptional.get();
    }


    @Override
    /**
     * @validating expenseCategoryOptional by uuid
     * @Validate if the List of expenseCategoryOptional is empty otherwise return record not found
     * Create the expenseCategory definition and update
     * @return a Success Message
     * * */
    public ApiResponse<String> updateExpenseCategory(UUID expenseCategoryId, ExpenseCategoryRequest request) {

        ExpenseCategory expenseCategory = validateExpenseCategory(expenseCategoryId);

        expenseCategory.setName(request.getName());
        expenseCategory.setDescription(request.getDescription());

        expenseCategoryRepository.save(expenseCategory);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Updated Successfully");

    }



    @Override
    /**
     * @validate expenseCategory by uuid
     * @Validate if expenseCategory is empty otherwise return record not found
     * @Delete expenseCategory
     * @return a Success Message
     * * */
    public ApiResponse<String> deleteExpenseCategory(UUID expenseCategoryId) {

        ExpenseCategory expenseCategory = validateExpenseCategory(expenseCategoryId);
        expenseCategoryRepository.delete(expenseCategory);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");

    }



}
