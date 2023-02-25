package fcmb.com.good.services.transaction;


import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.transactionRequest.PaymentCategoryRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.transactionResponse.PaymentCategoryResponse;
import fcmb.com.good.model.entity.transaction.PaymentCategory;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.repo.transaction.PaymentCategoryRepository;
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
public class PaymentCategoryServiceImpl implements PaymentCategoryService {

    private final UserRepository userRepository;
    private final PaymentCategoryRepository paymentCategoryRepository;


    @Override
    public ApiResponse<List<PaymentCategoryResponse>> getListOfPaymentCategory(int page, int size) {

        List<PaymentCategory> paymentCategoryList = paymentCategoryRepository.findAll(PageRequest.of(page,size)).toList();
        if(paymentCategoryList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(paymentCategoryList, PaymentCategoryResponse.class));

    }


    /**
     * @Validating existingPaymentCategoryOption by name
     * @Validate if the List of existingPaymentCategoryOption is empty otherwise return Duplicate Record
     * */
    private Optional<PaymentCategory> validateDuplicatePaymentCategory(String name) {
        Optional<PaymentCategory> paymentCategoryOptional = paymentCategoryRepository.findByName(name);
        return paymentCategoryOptional;
    }


    @Override
    public ApiResponse<String> addPaymentCategory(PaymentCategoryRequest request) {

        Optional<PaymentCategory> paymentCategoryOptional = validateDuplicatePaymentCategory(request.getName());

        if (!paymentCategoryOptional.isEmpty()) {
            return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
                    "Duplicate Record");
        }

        AppUser existingUser  = userRepository.findByUuid(request.getCreatedById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        PaymentCategory paymentCategory = new PaymentCategory();

        paymentCategory.setName(request.getName());
        paymentCategory.setDescription(request.getDescription());
        paymentCategory.setCreatedBy(existingUser);
        paymentCategory.setPostedBy(existingUser.getName());

        paymentCategoryRepository.save(paymentCategory);

        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Added successfully");

    }




    @Override
    public ApiResponse<PaymentCategoryResponse> getPaymentCategoryById(UUID paymentCategoryId) {

        Optional<PaymentCategory> paymentCategoryOptional = paymentCategoryRepository.findByUuid(paymentCategoryId);

        if(paymentCategoryOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        PaymentCategory paymentCategory = paymentCategoryOptional.get();

        return new ApiResponse<PaymentCategoryResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(paymentCategory,PaymentCategoryResponse.class));

    }

    /**
     * @validating PaymentCategoryOptional by uuid
     * @Validate if the List of PaymentCategory is empty otherwise return record not found
     * @return PaymentCategoryOptional
     * * */
    private PaymentCategory validatePaymentCategory(UUID uuid){
        Optional<PaymentCategory> paymentCategoryOptional = paymentCategoryRepository.findByUuid(uuid);
        if(paymentCategoryOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return paymentCategoryOptional.get();
    }


    @Override
    public ApiResponse<String> updatePaymentCategory(UUID paymentCategoryId, PaymentCategoryRequest request) {

        PaymentCategory paymentCategory = validatePaymentCategory(paymentCategoryId);

        paymentCategory.setName(request.getName());
        paymentCategory.setDescription(request.getDescription());

        paymentCategoryRepository.save(paymentCategory);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Updated Successfully");

    }



    @Override
    public ApiResponse<String> deletePaymentCategory(UUID paymentCategoryId) {

        PaymentCategory paymentCategory = validatePaymentCategory(paymentCategoryId);

        paymentCategoryRepository.delete(paymentCategory);

        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }




}
