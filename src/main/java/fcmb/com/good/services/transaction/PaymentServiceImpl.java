package fcmb.com.good.services.transaction;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.transactionRequest.PaymentRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.transactionResponse.PaymentResponse;
import fcmb.com.good.model.entity.transaction.Orders;
import fcmb.com.good.model.entity.transaction.Payment;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.repo.products.ProductRepository;
import fcmb.com.good.repo.transaction.OrdersRepository;
import fcmb.com.good.repo.transaction.PaymentRepository;
import fcmb.com.good.repo.user.CustomerRepository;
import fcmb.com.good.repo.user.UserRepository;
import fcmb.com.good.utills.MessageUtil;
import fcmb.com.good.utills.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private  final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrdersRepository ordersRepository;



    @Override
    /**
     * @Validate and Find the list of  Payment
     * @Validate if the List of Payment is empty otherwise return record not found*
     * @return the list of Payment and a Success Message
     * * */
    public ApiResponse<List<PaymentResponse>> getListOfPayment(int page, int size) {
        List<Payment> paymentList = paymentRepository.findAll(PageRequest.of(page,size)).toList();
        if(paymentList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(paymentList, PaymentResponse.class));
    }


    @Override
    /**
     * @Validate that no duplicate Payment is allowed
     * @Validate that PaymentCategory exists otherwise return record not found
     * Create the Payment definition and save
     * @return success message
     * * */
    public ApiResponse<String> addPayment(PaymentRequest request) {

        AppUser existingUser  = userRepository.findByUuid(request.getCreatedById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Orders existingOrders  = ordersRepository.findByUuid(request.getOrdersId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));


        Payment payment = new Payment();

        payment.setDescription(request.getDescription());
        payment.setAmount(existingOrders.getAmount());
        payment.setPaidBy(existingOrders.getOrderBy());
        payment.setPaymentMode(request.getPaymentMode());
        payment.setPaymentStatus("paid");
        payment.setPostedBy(existingUser.getName());
        payment.setTranReference("we435");
        payment.setOrder(existingOrders);

        paymentRepository.save(payment);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record created successfully");

    }



    /**
     * @validating PaymentOptional by uuid
     * @Validate if the List of Payment is empty otherwise return record not found
     * @return PaymentOptional
     * * */
    private Payment validatePayment(UUID uuid){
        Optional<Payment> paymentOptional = paymentRepository.findByUuid(uuid);
        if(paymentOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return paymentOptional.get();
    }


    @Override
    /**
     * @validating PaymentOptional by uuid
     * @Validate if the List of PaymentOptional is empty otherwise return record not found
     * Create the Payment definition and update
     * @return a Success Message
     * * */
    public ApiResponse<String> updatePayment(UUID paymentId,PaymentRequest request) {

        Payment payment = validatePayment(paymentId);

        payment.setDescription(request.getDescription());
        payment.setPaymentMode(request.getPaymentMode());
        payment.setPaymentStatus("paid");
        payment.setTranReference("we345");

        paymentRepository.save(payment);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Updated Successfully");
    }


    @Override
    /**
     * @validate Payment by uuid
     * @Validate if Payment is empty otherwise return record not found
     * @Delete Payment
     * @return a Success Message
     * * */
    public ApiResponse<String> deletePayment(UUID paymentId) {
        Payment payment = validatePayment(paymentId);
        paymentRepository.delete(payment);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }


    @Override
    public ApiResponse<List<PaymentResponse>> findPaymentByDate(LocalDateTime dateCreated) {

        List<Payment> paymentList = paymentRepository.findByDateCreated(dateCreated);

        if(paymentList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        Utils.extractDate(String.valueOf(paymentList));

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(paymentList, PaymentResponse.class));

    }

    @Override
    public ApiResponse<List<PaymentResponse>> findPaymentBySalesPerson(UUID userUuid) {

        List<Payment> paymentList = paymentRepository.findPaymentBySalesPerson(userUuid);

        if(paymentList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(paymentList,PaymentResponse.class));

    }

    @Override
    public ApiResponse<List<PaymentResponse>> findPaymentByCustomer(UUID uuid) {

        List<Payment> paymentList = paymentRepository.findPaymentByCustomer(uuid);

        if(paymentList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);


        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(paymentList,PaymentResponse.class));

    }


    @Override
    public ApiResponse<List<PaymentResponse>> getPaymentByOrderId(UUID orderId) {

        List<Payment> paymentList = paymentRepository.findByOrderId(orderId);

        if(paymentList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(paymentList,PaymentResponse.class));

    }


    @Override
    public ApiResponse<List<PaymentResponse>> findListOfPaymentByDateRange(int page, int size, String from, String to) {
        List<Payment> paymentList = paymentRepository.findByDateCreatedBetween(from,to);

        if(paymentList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(paymentList, PaymentResponse.class));
    }



}
