package fcmb.com.good.services.transaction;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.transactionRequest.PaymentRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.transactionResponse.PaymentResponse;
import fcmb.com.good.model.entity.kitchen.Kitchen;
import fcmb.com.good.model.entity.products.Product;
import fcmb.com.good.model.entity.rooms.Rooms;
import fcmb.com.good.model.entity.transaction.Maintenance;
import fcmb.com.good.model.entity.transaction.MaintenanceCategory;
import fcmb.com.good.model.entity.transaction.Payment;
import fcmb.com.good.model.entity.transaction.PaymentCategory;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.model.entity.user.Customer;
import fcmb.com.good.model.entity.user.Employee;
import fcmb.com.good.repo.kitchen.KitchenRepository;
import fcmb.com.good.repo.products.ProductRepository;
import fcmb.com.good.repo.rooms.RoomsRepository;
import fcmb.com.good.repo.transaction.PaymentCategoryRepository;
import fcmb.com.good.repo.transaction.PaymentRepository;
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
public class PaymentServiceImpl implements PaymentService {

    private  final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final RoomsRepository roomsRepository;
    private final KitchenRepository kitchenRepository;


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

        Customer existingCustomer  = customerRepository.findByUuid(request.getCurrentCustomerId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Product existingProduct  = productRepository.findByUuid(request.getCurrentProductId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Rooms existingRooms  = roomsRepository.findByUuid(request.getCurrentRoomId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Kitchen existingKitchen  = kitchenRepository.findByUuid(request.getCurrentKitchenId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));


        Payment payment = new Payment();

        payment.setCategory(existingProduct.getProductsCategory());
        payment.setAmount((existingProduct.getPrice())*(request.getQuantity()));
        payment.setServiceName(existingProduct.getName());
        payment.setPrice(existingProduct.getPrice());
        payment.setQuantity(request.getQuantity());
        payment.setAccountNo(request.getAccountNo());
        payment.setRoomNo(existingRooms.getServiceNumber());
        payment.setPaymentStatus(request.getPaymentStatus());
        payment.setPaymentDetails(request.getPaymentDetails());
        payment.setPaidBy(existingCustomer.getName());
        payment.setCreatedBy(existingUser);
        payment.setProduct(existingProduct);
        payment.setCustomer(existingCustomer);
        payment.setRooms(existingRooms);

        paymentRepository.save(payment);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record created successfully");

    }


    @Override
    /**
     * @Validating and Finding the list of PaymentOptional by uuid
     * @Validate if the List of PaymentOptional is empty otherwise return record not found
     * Create the Payment definition and get the PaymentOptional by uuid
     * @return the list of PaymentOptional and a Success Message
     * * */
    public ApiResponse<PaymentResponse> getPaymentById(UUID paymentId) {
        Optional<Payment> paymentOptional = paymentRepository.findByUuid(paymentId);

        if(paymentOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        Payment payment = paymentOptional.get();

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(payment,PaymentResponse.class));
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
//
//        payment.setCategory(request.getCategory());
//        payment.setAmount(request.getAmount());
        payment.setPaymentStatus(request.getPaymentStatus());
        payment.setPaymentDetails(request.getPaymentDetails());

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



}
