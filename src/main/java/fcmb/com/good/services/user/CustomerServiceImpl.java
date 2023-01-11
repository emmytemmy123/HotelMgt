package fcmb.com.good.services.user;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.filter.JwtFilter;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.request.userRequest.CustomerRequest;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.userResponse.CustomerResponse;
import fcmb.com.good.model.dto.response.userResponse.EmployeeResponse;
import fcmb.com.good.model.entity.user.Customer;
import fcmb.com.good.model.entity.user.User;
import fcmb.com.good.repo.user.CustomerRepository;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService  {

    private  final CustomerRepository customerRepository;

    private final JwtFilter jwtFilter;

    @Value("${FILE_UPLOAD_LOCATION}")
    private String uploadLocation;

    @Override
    public ApiResponse<List<CustomerResponse>> getListOfCustomer(int page, int size) {
        if(jwtFilter.isAdmin() || jwtFilter.isEmployee()){
            List<Customer> customerList = customerRepository.findAll(PageRequest.of(page,size)).toList();
            if(customerList.isEmpty())
                throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

            return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    Mapper.convertList(customerList, CustomerResponse.class));
        }
        return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
                "You are not Authorized");
    }

    @Override
    public ApiResponse<CustomerResponse> addCustomer(@RequestBody CustomerRequest request) throws IOException {
        if(jwtFilter.isAdmin() || jwtFilter.isEmployee()){
            Optional<Customer> customer  = validateCustomerByEmailId(request.getEmail());
            if(!customer.isEmpty()){
                return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
                        "Email Already Exist");
            }
            customerRepository.save(getCustomerFromRequest(request));
            return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    Mapper.convertObject(customer, CustomerResponse.class));

        }
        return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
                "You are not Authorized to add Customer");

    }


    @Override
    public ApiResponse<CustomerResponse> getCustomerById(UUID customerId) {
        if(jwtFilter.isAdmin() || jwtFilter.isEmployee()){
            Optional<Customer> customer = customerRepository.findByUuid(customerId);
            if(customer.isEmpty())
                throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

            Customer cm = customer.get();
            return new ApiResponse<CustomerResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    Mapper.convertObject(cm,CustomerResponse.class));
        }
        return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
                "You are not Authorized");
    }

    private Optional<Customer> validateCustomerByEmailId(String email){
        Optional<Customer> customer = customerRepository.findByEmailId(email);
        System.out.println(customer);
        return customer;
    }

    private Customer validateCustomer(UUID uuid){
    Optional<Customer> customer = customerRepository.findByUuid(uuid);
    if(customer.isEmpty())
        throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return customer.get();
    }

    private Customer getCustomerFromRequest(CustomerRequest request){
        Customer cus = new Customer();
        cus.setPostedBy(request.getPostedBy());
        cus.setName(request.getName());
        cus.setEmail(request.getEmail());
        cus.setAddress(request.getAddress());
        cus.setCountry(request.getCountry());
        cus.setCity(request.getCity());
        cus.setGender(request.getGender());
        cus.setPassword(request.getPassword());
        cus.setPhone(request.getPhone());
        cus.setUsername(request.getUsername());
        cus.setPhoto(request.getPhoto());
        cus.setRole(request.getRole());
        cus.setNin(request.getNin());
        return cus;
    }

    @Override
    public ApiResponse<CustomerResponse> updateCustomer(UUID customerId, @RequestBody CustomerRequest cst) {
        if(jwtFilter.isAdmin() || jwtFilter.isEmployee()){
            Customer customer = validateCustomer(customerId);
            customer.setName(cst.getName());
            customer.setEmail(cst.getEmail());
            customer.setPhone(cst.getPhone());
            customer.setAddress(cst.getAddress());
            customer.setNin(cst.getNin());
            customer.setPhoto(cst.getPhoto());
            customer.setUsername(cst.getUsername());

            customer = customerRepository.save(customer);
            return new ApiResponse<CustomerResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    Mapper.convertObject(customer,CustomerResponse.class));
        }
        return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
                "You are not Authorized");
    }


    @Override
    public ApiResponse<String> deleteCustomer(UUID customerId) {
        if(jwtFilter.isAdmin() || jwtFilter.isEmployee()){
            Customer customer = validateCustomer(customerId);
            customerRepository.delete(customer);
            return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    "Record Deleted successfully");
        }
        return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
                "You are not Authorized");
    }



}
