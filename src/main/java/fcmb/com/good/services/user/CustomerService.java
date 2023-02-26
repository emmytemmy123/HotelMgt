package fcmb.com.good.services.user;

import fcmb.com.good.model.dto.request.userRequest.*;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.userResponse.CustomerResponse;
import fcmb.com.good.model.entity.user.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


public interface CustomerService {

    ApiResponse<List<CustomerResponse>> getListOfCustomer(int page, int size);

    ApiResponse<String> addCustomer(CustomerRequest request) throws IOException;

    ApiResponse<CustomerResponse> getCustomerById(UUID customerId);

    ApiResponse<String> updateCustomer( UUID customerId, CustomerRequest cst);

    ApiResponse<String> deleteCustomer(UUID customerId);

    ApiResponse<String> resetPassword(String email, changeCustomerPasswordRequest request);

    ApiResponse<String> forgotCustomerPassword(String email) throws MessagingException;

    ApiResponse<String> loginCustomer(String email, loginCustomerRequest request);



}
