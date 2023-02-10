package fcmb.com.good.services.transaction;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.transactionRequest.ExpenseRequestRequest;
import fcmb.com.good.model.dto.response.transactionResponse.ExpenseRequestResponse;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.entity.transaction.ExpenseRequest;
import fcmb.com.good.repo.transaction.ExpenseRequestRepository;
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
public class ExpenseRequestServiceImpl implements ExpenseRequestService {
    private final ExpenseRequestRepository expenseRequestRepository;

    @Override
    public ApiResponse<List<ExpenseRequestResponse>> getListOfExpenseRequest(int page, int size) {
        List<ExpenseRequest> expenseRequestList = expenseRequestRepository.findAll(PageRequest.of(page,size)).toList();
        if(expenseRequestList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(expenseRequestList, ExpenseRequestResponse.class));
    }

    @Override
    public ApiResponse<String> addExpenseRequest(@RequestBody ExpenseRequestRequest request) {
        ExpenseRequest expenseRequest = Mapper.convertObject(request,ExpenseRequest.class);
        expenseRequestRepository.save(expenseRequest);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record added Successfully");
    }

    @Override
    public ApiResponse<ExpenseRequestResponse> getExpenseRequestById(@RequestParam UUID expenseRequestId) {
        Optional<ExpenseRequest> expenseRequest = expenseRequestRepository.findByUuid(expenseRequestId);

        if(expenseRequest.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        ExpenseRequest er = expenseRequest.get();
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(er,ExpenseRequestResponse.class));

    }

    private ExpenseRequest validateExpenseRequest(UUID uuid){
        Optional<ExpenseRequest> expenseRequest = expenseRequestRepository.findByUuid(uuid);
        if(expenseRequest.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return expenseRequest.get();
    }

    @Override
    public ApiResponse<String> updateExpenseRequest(@RequestParam UUID expenseRequestId,
                                                                    @RequestBody ExpenseRequestRequest request) {
        ExpenseRequest expenseRequest = validateExpenseRequest(expenseRequestId);
        expenseRequest.setExpense_id(request.getExpense_id());
        expenseRequest.setEmployee_id(request.getEmployee_id());
        expenseRequest.setQuantity(request.getQuantity());
        expenseRequest.setAmount(request.getAmount());
        expenseRequest.setAccount_no(request.getAccount_no());
        expenseRequest.setStatus(request.getStatus());

        expenseRequestRepository.save(expenseRequest);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Update Successfully");
    }

    @Override
    public ApiResponse<String> deleteExpenseRequest(@RequestParam("id") UUID expenseRequestId) {
        ExpenseRequest expenseRequest = validateExpenseRequest(expenseRequestId);
        expenseRequestRepository.delete(expenseRequest);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }


}
