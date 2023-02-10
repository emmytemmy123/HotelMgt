package fcmb.com.good.services.transaction;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.transactionRequest.AccountChartRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.transactionResponse.AccountChartResponse;
import fcmb.com.good.model.entity.transaction.AccountChart;
import fcmb.com.good.repo.transaction.AccountChartRepository;
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

    @Override
    public ApiResponse<List<AccountChartResponse>> getListOfAccountChart(int page, int size) {
        List<AccountChart> accountChartList = accountChartRepository.findAll(PageRequest.of(page,size)).toList();
        if(accountChartList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(accountChartList, AccountChartResponse.class));

    }

    @Override
    public ApiResponse<String> addAccountChart(@RequestBody AccountChartRequest request) {
        AccountChart accountChart = Mapper.convertObject(request,AccountChart.class);
        accountChartRepository.save(accountChart);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record added Successfully");
    }

    @Override
    public ApiResponse<AccountChartResponse>getAccountChartById(@RequestParam("id") UUID accountChartId) {
        Optional<AccountChart> accountChart = accountChartRepository.findByUuid(accountChartId);

        if(accountChart.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        AccountChart cm = accountChart.get();
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(cm,AccountChartResponse.class));

    }

    private AccountChart validateAccountChart(UUID uuid){
        Optional<AccountChart> accountChart = accountChartRepository.findByUuid(uuid);
        if(accountChart.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return accountChart.get();
    }

    @Override
    public ApiResponse<String> updateAccountChart(UUID accountChartId,
                                                                @RequestBody AccountChartRequest request) {
        AccountChart accountChart = validateAccountChart(accountChartId);
        accountChart.setCategory_id(request.getCategory_id());
        accountChart.setCode(request.getCode());
        accountChart.setAccount_name(request.getAccount_name());
        accountChart.setBalance(request.getBalance());

        accountChart = accountChartRepository.save(accountChart);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Updated Successfully");
    }


    @Override
    public ApiResponse<String> deleteAccountChart(UUID accountChartId) {
        AccountChart accountChart = validateAccountChart(accountChartId);
        accountChartRepository.delete(accountChart);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }


}
