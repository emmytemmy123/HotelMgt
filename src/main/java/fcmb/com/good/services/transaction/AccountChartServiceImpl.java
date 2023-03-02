package fcmb.com.good.services.transaction;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.transactionRequest.AccountChartRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.transactionResponse.AccountChartResponse;
import fcmb.com.good.model.entity.transaction.AccountCategory;
import fcmb.com.good.model.entity.transaction.AccountChart;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.model.entity.user.Customer;
import fcmb.com.good.repo.transaction.AccountCategoryRepository;
import fcmb.com.good.repo.transaction.AccountChartRepository;
import fcmb.com.good.repo.user.CustomerRepository;
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
public class AccountChartServiceImpl implements AccountChartService {

    private  final AccountChartRepository accountChartRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final AccountCategoryRepository accountCategoryRepository;


    @Override
    /**
     * @Validate and Find the list of  AccountChart
     * @Validate if the List of AccountChart is empty otherwise return record not found*
     * @return the list of AccountChart and a Success Message
     * * */
    public ApiResponse<List<AccountChartResponse>> getListOfAccountChart(int page, int size) {
        List<AccountChart> accountChartList = accountChartRepository.findAll(PageRequest.of(page,size)).toList();
        if(accountChartList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(accountChartList, AccountChartResponse.class));

    }

    /**
     * @Validating existingAccountChartOptional by accountNo
     * @Validating existingAccountChartOptional by accountNo
     * @Validate the List of existingAccountChartOptional and existingAccountChartOptional is present otherwise return Duplicate Record*
     * * */
    private void validateDuplicateAccountChart(Integer accountNo){
        Optional<AccountChart> accountChartOptional = accountChartRepository.findByAccountNo(accountNo);

        if(accountChartOptional.isPresent() )
            throw new RecordNotFoundException("Duplicate record");
    }


    @Override
    /**
     * @Validate that no duplicate AccountChart is allowed
     * @Validate that AccountChart exists otherwise return record not found
     * Create the AccountChart definition and save
     * @return success message
     * * */
    public ApiResponse<String> addAccountChart(AccountChartRequest request) {

        validateDuplicateAccountChart(request.getAccountNo());

        AccountCategory existingAccountCategory = accountCategoryRepository.findByUuid
                        (request.getAccountCategoryId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        AppUser existingUser  = userRepository.findByUuid(request.getCreatedById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Customer existingCustomer  = customerRepository.findByUuid(request.getCurrentCustomerId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        AccountChart accountChart = new AccountChart();

        accountChart.setCategory(request.getCategory());
        accountChart.setCode(request.getCode());
        accountChart.setAccountName(request.getAccountName());
        accountChart.setAccountNo(request.getAccountNo());
        accountChart.setBalance(request.getBalance());
        accountChart.setCurrentCustomer(existingCustomer.getName());
        accountChart.setCustomer(existingCustomer);
        accountChart.setCreatedBy(existingUser);
        accountChart.setAccountCategory(existingAccountCategory);

        accountChartRepository.save(accountChart);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record created successfully");

    }



    @Override
    /**
     * @Validating and Finding the list of AccountChartOptional by uuid
     * @Validate if the List of AccountChartOptional is empty otherwise return record not found
     * Create the AccountChart definition and get the expenseCategoryOptional by uuid
     * @return the list of AccountChart and a Success Message
     * * */
    public ApiResponse<AccountChartResponse>getAccountChartById( UUID accountChartId) {
        Optional<AccountChart> accountChart = accountChartRepository.findByUuid(accountChartId);

        if(accountChart.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        AccountChart cm = accountChart.get();

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(cm,AccountChartResponse.class));

    }

    /**
     * @Validating existingAccountChartOption by category
     * @Validate if the List of existingAccountChartOption is empty otherwise return Duplicate Record
     * */
    private AccountChart validateAccountChart(UUID uuid){
        Optional<AccountChart> accountChart = accountChartRepository.findByUuid(uuid);
        if(accountChart.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return accountChart.get();
    }

    @Override
    /**
     * @Validating and Finding the list of AccountChartOptional by uuid
     * @Validate if the List of AccountChartOptional is empty otherwise return record not found
     * Create the AccountChart definition and get the AccountChartOptional by uuid
     * @return the list of AccountChart and a Success Message
     * * */
    public ApiResponse<String> updateAccountChart(UUID accountChartId, AccountChartRequest request) {

        AccountChart accountChart = validateAccountChart(accountChartId);

        accountChart.setAccountName(request.getAccountName());
//        accountChart.setBalance(request.getBalance());

        accountChartRepository.save(accountChart);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Updated Successfully");
    }


    @Override
    /**
     * @validate AccountChart by uuid
     * @Validate if AccountChart is empty otherwise return record not found
     * @Delete AccountChart
     * @return a Success Message
     * * */
    public ApiResponse<String> deleteAccountChart(UUID accountChartId) {
        AccountChart accountChart = validateAccountChart(accountChartId);
        accountChartRepository.delete(accountChart);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }


}
