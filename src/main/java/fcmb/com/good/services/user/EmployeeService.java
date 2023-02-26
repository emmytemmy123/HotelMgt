package fcmb.com.good.services.user;

import fcmb.com.good.model.dto.request.userRequest.*;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.userResponse.EmployeeResponse;

import javax.mail.MessagingException;
import java.util.List;
import java.util.UUID;

public interface EmployeeService {


   ApiResponse<List<EmployeeResponse>> getListOfEmployee(int page, int size);

    ApiResponse<String> addEmployee(EmployeeRequest request);

    ApiResponse<EmployeeResponse> getEmployeeById(UUID employeeId);

    ApiResponse<String> updateEmployee(UUID employeeId, EmployeeRequest employee);

    ApiResponse<String> deleteEmployee(UUID employeeId);

    ApiResponse<String> resetPassword(String email, changeEmployeePasswordRequest request);

    ApiResponse<String> forgotEmployeePassword(String email) throws MessagingException;

    ApiResponse<String> loginEmployee(String email, loginEmployeeRequest request);




}
