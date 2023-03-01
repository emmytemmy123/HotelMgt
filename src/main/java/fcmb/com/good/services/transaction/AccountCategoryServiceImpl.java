package fcmb.com.good.services.transaction;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.transactionRequest.AccountCategoryRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.transactionResponse.AccountCategoryResponse;
import fcmb.com.good.model.entity.transaction.AccountCategory;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.repo.transaction.AccountCategoryRepository;
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
public class AccountCategoryServiceImpl implements AccountCategoryService {

    private  final AccountCategoryRepository accountCategoryRepository;
    private final UserRepository userRepository;


    @Override
    /**
     * @Validate and Find the list of  AccountCategory
     * @Validate if the List of AccountCategory is empty otherwise return record not found*
     * @return the list of AccountCategory and a Success Message
     * * */
    public ApiResponse<List<AccountCategoryResponse>> getListOfAccountCategory(int page, int size) {
        List<AccountCategory> accountCategoryList = accountCategoryRepository.findAll(PageRequest.of(page,size)).toList();
        if(accountCategoryList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(accountCategoryList, AccountCategoryResponse.class));
    }


    /**
     * @Validating existingAccountingCategoryOption by currency
     * @Validate if the List of existingAccountingCategoryOption is empty otherwise return Duplicate Record
     * */
    private Optional<AccountCategory> validateDuplicateAccountCategory(String currency) {
        Optional<AccountCategory> existingAccountingCategoryOption = accountCategoryRepository.findByCurrency(currency);
        return existingAccountingCategoryOption;
    }


    @Override
    /**
     * @Validate that no duplicate AccountCategory is allowed
     * @Validate that AccountCategory exists otherwise return record not found
     * Create the AccountCategory definition and save
     * @return success message
     * * */
    public ApiResponse<String> addAccountCategory(@RequestBody AccountCategoryRequest request) {

        Optional<AccountCategory> accountCategoryOptional = validateDuplicateAccountCategory(request.getCurrency());

        AppUser existingUser  = userRepository.findByUuid(request.getCreatedById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        if (!accountCategoryOptional.isEmpty()) {
            return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
                    "Duplicate Record");
        }

        AccountCategory accountCategory = new AccountCategory();

        accountCategory.setCode(request.getCode());
        accountCategory.setCurrency(request.getCurrency());
        accountCategory.setAccountType(request.getAccountType());
        accountCategory.setPostedBy(existingUser.getName());
        accountCategory.setCreatedBy(existingUser);

        accountCategoryRepository.save(accountCategory);
        //roomCategoryRepository.save(getRoomTypeFromRequest(request));

        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Added successfully");

    }

    @Override
    /**
     * @Validating and Finding the list of AccountCategoryOptional by uuid
     * @Validate if the List of AccountCategoryOptional is empty otherwise return record not found
     * Create the AccountCategory definition and get the AccountCategoryOptional by uuid
     * @return the list of AccountCategory and a Success Message
     * * */
    public ApiResponse<AccountCategoryResponse> getAccountCategoryById(@RequestParam("id") UUID accountCategoryId) {
        Optional<AccountCategory> accountCategory = accountCategoryRepository.findByUuid(accountCategoryId);

        if(accountCategory.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        AccountCategory accountCategory1 = accountCategory.get();

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(accountCategory1,AccountCategoryResponse.class));

    }


    /**
     * @validating AccountCategoryOptional by uuid
     * @Validate if the List of AccountCategoryOptional is empty otherwise return record not found
     * @return AccountCategoryOptional
     * * */
    private AccountCategory validateAccountCategory(UUID uuid){
        Optional<AccountCategory> accountCategory = accountCategoryRepository.findByUuid(uuid);
        if(accountCategory.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return accountCategory.get();
    }

    @Override
    /**
     * @validating AccountCategoryOptional by uuid
     * @Validate if the List of AccountCategoryOptional is empty otherwise return record not found
     * Create the AccountCategory definition and update
     * @return a Success Message
     * * */
    public ApiResponse<String> updateAccountCategory(UUID accountCategoryId, AccountCategoryRequest request) {

        AccountCategory accountCategory = validateAccountCategory(accountCategoryId);
        accountCategory.setCode(request.getCode());
        accountCategory.setCurrency(request.getCurrency());
        accountCategory.setAccountType(request.getAccountType());

        accountCategoryRepository.save(accountCategory);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record updated Successfully");
    }

    @Override
    /**
     * @validate AccountCategory by uuid
     * @Validate if AccountCategory is empty otherwise return record not found
     * @Delete AccountCategory
     * @return a Success Message
     * * */
    public ApiResponse<String> deleteAccountCategory(UUID accountCategoryId) {
        AccountCategory accountCategory = validateAccountCategory(accountCategoryId);
        accountCategoryRepository.delete(accountCategory);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }


}
