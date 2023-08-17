package fcmb.com.good.services.transaction;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.enums.Status;
import fcmb.com.good.model.dto.request.transactionRequest.PaymentRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.transactionResponse.PaymentResponse;
import fcmb.com.good.model.entity.activityLog.ActivityLog;
import fcmb.com.good.model.entity.services.SubServiceRequest;
import fcmb.com.good.model.entity.transaction.Orders;
import fcmb.com.good.model.entity.transaction.Payment;
import fcmb.com.good.repo.activityLog.ActivityLogRepository;
import fcmb.com.good.repo.services.SubServiceRequestRepository;
import fcmb.com.good.repo.transaction.OrdersRepository;
import fcmb.com.good.repo.transaction.PaymentRepository;
import fcmb.com.good.repo.user.UsersRepository;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private  final PaymentRepository paymentRepository;
    private final UsersRepository usersRepository;
    private final SubServiceRequestRepository subServiceRequestRepository;
    private final OrdersRepository ordersRepository;
    private final ActivityLogRepository activityLogRepository;



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

        Orders existingOrders  = ordersRepository.findByUuid(request.getOrdersId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Payment payment = new Payment();

        payment.setAmountPaid(request.getAmountPaid());
        payment.setPaymentMode(request.getPaymentMode());

        int valueToBalance = (int) (existingOrders.getAmountDue() - (payment.getAmountPaid()));
        existingOrders.setProductStatus(valueToBalance==0? Status.PAID.label: Status.TO_BALANCE.label);

        int valueToBalances = (int) (existingOrders.getAmountDue() - (payment.getAmountPaid()));
        payment.setPaymentStatus(valueToBalances==0? Status.PAID.label: Status.TO_BALANCE.label);

        payment.setTranReference(request.getTranReference());
        payment.setOrder(existingOrders);
        payment.setTotalAmount(existingOrders.getAmount());
        payment.setBalance(existingOrders.getAmountDue() - (payment.getAmountPaid()));
        payment.setPostedBy(existingOrders.getOrderBy());

        paymentRepository.save(payment);

        existingOrders.setAmountDue(existingOrders.getAmountDue() - (payment.getAmountPaid()));

        ordersRepository.save(existingOrders);

        ActivityLog activityLog = new ActivityLog();
        activityLog.setName("addPayment");
        activityLog.setCategory("add");
        activityLog.setDescription("this is a payment log");
        activityLog.setPerformedBy(existingOrders.getOrderBy());
        activityLog.setPerformedDate(LocalDateTime.now());
        activityLog.setAmountPaid(request.getAmountPaid());

        activityLogRepository.save(activityLog);

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
    public ApiResponse<List<PaymentResponse>> findPaymentByDate(String dateCreated) {

        List<Payment> paymentList = paymentRepository.findByDateCreated(dateCreated);

        if(paymentList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(paymentList, PaymentResponse.class));

    }

    @Override
    public ApiResponse<List<PaymentResponse>> findPaymentBySalesPerson(UUID userUuid) {

//        List<Payment> paymentList = paymentRepository.findPaymentBySalesPerson(userUuid);
//
//        if(paymentList.isEmpty())
//            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
//
//        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
//                Mapper.convertList(paymentList,PaymentResponse.class));
            return null;
    }

    @Override
    public ApiResponse<List<PaymentResponse>> findPaymentByCustomer(UUID uuid) {

//        List<Payment> paymentList = paymentRepository.findPaymentByCustomer(uuid);
//
//        if(paymentList.isEmpty())
//            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
//
//
//        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
//                Mapper.convertList(paymentList,PaymentResponse.class));
        return null;
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
    public ApiResponse<List<PaymentResponse>> findListOfPaymentByDateRange(String from, String to) {

        List<Payment> paymentList = paymentRepository.findByDateCreatedBetween(from,to);

        if(paymentList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(paymentList, PaymentResponse.class));
    }



}
