package fcmb.com.good.services.transaction;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.transactionRequest.PaymentRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.transactionResponse.PaymentResponse;
import fcmb.com.good.model.entity.transaction.Payment;
import fcmb.com.good.repo.transaction.PaymentRepository;
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
public class PaymentServiceImpl implements PaymentService {
    private  final PaymentRepository paymentRepository;

    @Override
    public ApiResponse<List<PaymentResponse>> getListOfPayment(int page, int size) {
        List<Payment> paymentList = paymentRepository.findAll(PageRequest.of(page,size)).toList();
        if(paymentList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(paymentList, PaymentResponse.class));
    }

    @Override
    public ApiResponse<String> addPayment(@RequestBody PaymentRequest request) {
        Payment payment = Mapper.convertObject(request,Payment.class);

        paymentRepository.save(payment);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record added Successfully");
    }

    @Override
    public ApiResponse<PaymentResponse> getPaymentById(@RequestParam("id") UUID paymentId) {
        Optional<Payment> payment = paymentRepository.findByUuid(paymentId);

        if(payment.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        Payment cm = payment.get();

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(cm,PaymentResponse.class));

    }

    private Payment validatePayment(UUID uuid){
        Optional<Payment> payment = paymentRepository.findByUuid(uuid);
        if(payment.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return payment.get();
    }

    @Override
    public ApiResponse<String> updatePayment(UUID paymentId, @RequestBody PaymentRequest request) {
        Payment payment = validatePayment(paymentId);
        payment.setCustomer_id(request.getCustomer_id());
        payment.setPayment_type(request.getPayment_type());
        payment.setAmount(request.getAmount());
        payment.setPayment_status(request.getPayment_status());
        payment.setPayment_details(request.getPayment_details());

        paymentRepository.save(payment);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Updated Successfully");
    }

    @Override
    public ApiResponse<String> deletePayment(UUID paymentId) {
        Payment payment = validatePayment(paymentId);
        paymentRepository.delete(payment);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }


}
