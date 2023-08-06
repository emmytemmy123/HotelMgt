package fcmb.com.good.services.user;

import fcmb.com.good.common.UserConstant;
import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.filter.JwtFilter;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.request.userRequest.CustomerRequest;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.userRequest.changeCustomerPasswordRequest;
import fcmb.com.good.model.dto.request.userRequest.loginCustomerRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.userResponse.CustomerResponse;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.model.entity.user.Customer;
import fcmb.com.good.repo.user.CustomerRepository;
import fcmb.com.good.repo.user.UserRepository;
import fcmb.com.good.utills.EmailUtils;
import fcmb.com.good.utills.MessageUtil;
import fcmb.com.good.utills.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService  {

    private  final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final EmailUtils emailUtils;
    private final JwtFilter jwtFilter;

    @Value("${FILE_UPLOAD_LOCATION}")
    private String uploadLocation;

    @Override
    /**
     * @Finding the list of Customer
     * @Validate if the List of customer is empty otherwise return record not found
     * @return the list of customer and a Success Message*
     * * */
    public ApiResponse<List<CustomerResponse>> getListOfCustomer(int page, int size) {

            List<Customer> customerList = customerRepository.findAll(PageRequest.of(page,size)).toList();
            if(customerList.isEmpty())
                throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

            return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    Mapper.convertList(customerList, CustomerResponse.class));
        }

    @Override
    /**
     * @Validate that no duplicate users is allowed*
     * @Validate that user exists otherwise return record not found*
     * Create user definition and save
     * @return success message
     * * */
    public ApiResponse<String> addCustomer(CustomerRequest request) throws IOException {

        Optional<Customer> customer  = validateCustomerByEmailId(request.getEmail());

            if(!customer.isEmpty()){
                return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
                        "Email Already Exist");
            }
            customerRepository.save(getCustomerFromRequest(request));
            return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    "Record added Successfully");
        }



    @Override
    /**
     * @Finding the list of customerOptional by uuid*
     * @Validate if the List of customerOptional is empty otherwise return record not found
     * Create the customer definition and get the customer
     * @return the list of customer and a Success Message
     * * */
    public ApiResponse<CustomerResponse> getCustomerById(UUID customerId) {
//        if(jwtFilter.isAdmin() || jwtFilter.isEmployee()){
            Optional<Customer> customerOptional = customerRepository.findByUuid(customerId);

            if(customerOptional.isEmpty())
                throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

            Customer customer = customerOptional.get();
            return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    Mapper.convertObject(customer,CustomerResponse.class));
        }
//        return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
//                "You are not Authorized");
//    }


    /**
     * @Validating existingCustomerOptional by Email
     * @Validate if existingCustomerOptional is empty otherwise return Duplicate Record
     * return existingCustomerOptional
     * * */
    private Optional<Customer> validateCustomerByEmailId(String email){
        Optional<Customer> existingCustomerOptional = customerRepository.findByEmailId(email);
        return existingCustomerOptional;
    }


    /**
     * @validating customerOptional by uuid
     * @Validate if the List of customer is empty otherwise return record not found
     * @return customerOptional
     * * */
    private Customer validateCustomer(UUID uuid){
    Optional<Customer> customerOptional = customerRepository.findByUuid(uuid);
    if(customerOptional.isEmpty())
        throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return customerOptional.get();
    }


    /**
     * Set and get the customers parameters
     */
    private Customer getCustomerFromRequest(CustomerRequest request) throws IOException {

        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setAddress(request.getAddress());
        customer.setCountry(request.getCountry());
        customer.setCity(request.getCity());
        customer.setGender(request.getGender());
        customer.setPassword(request.getPassword());
        customer.setPhone(request.getPhone());
        customer.setUsername(request.getUsername());
        customer.setPhoto(request.getPhoto());
        customer.setNin(request.getNin());
        customer.setRoles(UserConstant.DEFAULT_ROLE);//USER


        return customer;
    }


    @Override
    /**
     * @validating customerOptional by uuid
     * @Validate if the List of customer is empty otherwise return record not found
     * Create the customer definition and update
     * @return a Success Message
     * * */
    public ApiResponse<String> updateCustomer(UUID customerUuid, @RequestBody CustomerRequest request) {
            Customer customer = validateCustomer(customerUuid);

        if (request.getName() != null) {
            customer.setName(request.getName());
        }

        if (request.getAddress() != null) {
            customer.setAddress(request.getAddress());
        }
        if (request.getCountry() != null) {
            customer.setCountry(request.getCountry());
        }
        if (request.getCity() != null) {
            customer.setCity(request.getCity());
        }
        if (request.getGender() != null) {
            customer.setGender(request.getGender());
        }
        if (request.getPhone() != null) {
            customer.setPhone(request.getPhone());
        }

        if (request.getPhoto() != null) {
            customer.setPhoto(request.getPhoto());
        }
        if (request.getNin() != null) {
            customer.setNin(request.getNin());
        }

            customerRepository.save(customer);
            return new ApiResponse<String>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    "Record Updated Successfully");
        }



    @Override
    /**
     * @validating customer by uuid
     * @Validate if customer is empty otherwise return record not found
     * @Delete customer
     * @return a Success Message
     * * */
    public ApiResponse<String> deleteCustomer(UUID customerId) {
//        if(jwtFilter.isAdmin() || jwtFilter.isEmployee()){
            Customer customer = validateCustomer(customerId);
            customerRepository.delete(customer);
            return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    "Record Deleted successfully");
        }
//        return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
//                "You are not Authorized");
//    }

    @Override
    /**
     * @Validating existingCustomer by Email
     * @Validate customer password and change password
     * @Save the new password
     * @Return a Success Message if oldPassword is correct
     * @Return a Failed Message if oldPassword is Incorrect
     */
    public ApiResponse<String> resetPassword(String email, changeCustomerPasswordRequest request) {
        Customer customer = customerRepository.findByEmail(email);

        if(customer.getPassword().equals(request.getOldPassword())){
            customer.setPassword(request.getNewPassword());
            customerRepository.save(customer);

            return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    "Password Changed successfully");
        }
        return new ApiResponse(AppStatus.FAILED.label, HttpStatus.BAD_REQUEST.value(),
                "Incorrect Old Password");
    }

    @Override
    /**
     * @Validating existingCustomer by Email
     * @Getting email and password of customer and sending it to customer`s Email
     * @Return a Success Message
     */
    public ApiResponse<String> forgotCustomerPassword(String email) throws MessagingException {

        Customer customer = customerRepository.findByEmail(email);

        emailUtils.forgotMail(customer.getEmail(), "Credentials by Hotel Management System", customer.getPassword() );

        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Check your email for credentials");
    }


    @Override
    /**
     * @Validating and finding existingCustomer by Email
     * @Validate customer email and password
     * @Return a Success Message if email and password is correct
     * @Return a Failed Message if email and password is Incorrect
     */
    public ApiResponse<String> loginCustomer(String email, loginCustomerRequest request) {
        Customer customer = customerRepository.findByEmail(email);

        if (customer.getEmail().equals(request.getEmail())
                && customer.getPassword().equals(request.getPassword())) {

            return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    "Customer login successfully");
        }

        return new ApiResponse(AppStatus.FAILED.label, HttpStatus.BAD_REQUEST.value(),
                "Incorrect Email or Password");
    }

    private String getStoreLocationPath() {return Utils.baseDir(uploadLocation).getPath();
    }

}
