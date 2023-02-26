package fcmb.com.good.services.transaction;

import fcmb.com.good.model.dto.request.transactionRequest.AccountChartRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.transactionResponse.AccountChartResponse;
import fcmb.com.good.model.entity.transaction.AccountChart;

import java.util.List;
import java.util.UUID;

public interface AccountChartService {


    ApiResponse<List<AccountChartResponse>> getListOfAccountChart(int page, int size);

    ApiResponse<String> addAccountChart(AccountChartRequest request);

    ApiResponse<AccountChartResponse> getAccountChartById(UUID accountChartId);

    ApiResponse<String>updateAccountChart( UUID accountChartId, AccountChartRequest request);

    ApiResponse<String> deleteAccountChart(UUID accountChartId);




}
