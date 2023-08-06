package fcmb.com.good.services.user;

import fcmb.com.good.common.UserConstant;
import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.filter.JwtFilter;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.userRequest.EmployeeRequest;
import fcmb.com.good.model.dto.request.userRequest.changeEmployeePasswordRequest;
import fcmb.com.good.model.dto.request.userRequest.loginEmployeeRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.userResponse.EmployeeResponse;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.model.entity.user.Employee;
import fcmb.com.good.repo.user.EmployeeRepository;
import fcmb.com.good.repo.user.UserRepository;
import fcmb.com.good.utills.EmailUtils;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private  final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final EmailUtils emailUtils;
    private final JwtFilter jwtFilter;


    @Override
    /**
     * @Finding the list of Employee
     * @Validate if the List of Employee is empty otherwise return record not found
     * @return the list of Employee and a Success Message
     * * */
    public ApiResponse<List<EmployeeResponse>> getListOfEmployee(int page, int size) {
//        if(jwtFilter.isAdmin()){
            List<Employee> employeeList = employeeRepository.findAll(PageRequest.of(page,size)).toList();
            if(employeeList.isEmpty())
                throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

            return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    Mapper.convertList(employeeList, EmployeeResponse.class));
        }
//        return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
//                "You are not Authorized");
//    }


    @Override
    /**
     * @Validate that no duplicate Employee is allowed
     * @Validate that Employee exists otherwise return record not found*
     * Create Employee definition and save
     * @return success message
     * * */
    public ApiResponse<String> addEmployee(@RequestBody EmployeeRequest request) {
//        if(jwtFilter.isAdmin()){
            Optional<Employee> employeeOption  = validateEmployeeByEmailId(request.getEmail());

            if(!employeeOption.isEmpty()){
                return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
                        "Email Already Exist");
            }
            employeeRepository.save(getEmployeeFromRequest(request));
            return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    "Record added Successfully");
        }
//        return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
//                "You are not Authorized");
//    }


    @Override
    /**
     * @Finding the list of employeeOptional by uuid
     * @Validate if the List of employeeOptional is empty otherwise return record not found
     * Create the employee definition and get the employee
     * @return the list of employee and a Success Message* *
     * * */
    public ApiResponse<EmployeeResponse> getEmployeeById(@RequestParam("id") UUID employeeId) {
        Optional<Employee> employeeOptional = employeeRepository.findByUuid(employeeId);

        if(employeeOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        Employee employee = employeeOptional.get();

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(employee,EmployeeResponse.class));

    }

    /**
     * @Validating existingEmployeeOptional by Email
     * @Validate if existingEmployeeOptional is empty otherwise return Duplicate Record
     * return existingEmployeeOptional
     * * */
    private Optional<Employee> validateEmployeeByEmailId(String email) {
        Optional<Employee> existingEmployeeOptional = employeeRepository.findByEmailId(email);
        return existingEmployeeOptional;
    }

    /**
     * @validating employeeOptional by uuid*
     * @Validate if the List of employee is empty otherwise return record not found
     * @return employeeOptional
     * * */
    private Employee validateEmployee(UUID uuid) {
        Optional<Employee> employeeOptional = employeeRepository.findByUuid(uuid);
        if (employeeOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return employeeOptional.get();
    }


    /**
     * Set and get the employee parameters
     */
    private Employee getEmployeeFromRequest(EmployeeRequest request){

        AppUser existingUser  = userRepository.findByUuid(request.getCreatedById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setAddress(request.getAddress());
        employee.setCountry(request.getCountry());
        employee.setCity(request.getCity());
        employee.setGender(request.getGender());
        employee.setPassword(request.getPassword());
        employee.setPhone(request.getPhone());
        employee.setUsername(request.getUsername());
        employee.setPhoto(request.getPhoto());
        employee.setRoles(request.getRole());
        employee.setDesignation(request.getDesignation());
        employee.setCreatedBy(existingUser);
        employee.setRoles(UserConstant.DEFAULT_ROLE);//USER


        return employee;
    }


    @Override
    /**
     * @validating employeeOptional by uuid
     * @Validate if the List of employee is empty otherwise return record not found
     * Create the employee definition and update
     * @return a Success Message
     * * */
    public ApiResponse<String> updateEmployee(UUID employeeId, @RequestBody EmployeeRequest request) {
            Employee employee = validateEmployee(employeeId);

        if (request.getName() != null) {
            employee.setName(request.getName());
        }

        if (request.getAddress() != null) {
            employee.setAddress(request.getAddress());
        }
        if (request.getCountry() != null) {
            employee.setCountry(request.getCountry());
        }
        if (request.getCity() != null) {
            employee.setCity(request.getCity());
        }
        if (request.getGender() != null) {
            employee.setGender(request.getGender());
        }
        if (request.getPhone() != null) {
            employee.setPhone(request.getPhone());
        }

        if (request.getPhoto() != null) {
            employee.setPhoto(request.getPhoto());
        }


            employeeRepository.save(employee);
            return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    "Record Updated Successfully");
        }
//        return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
//                "You are not Authorized");
//    }


    @Override
    /**
     * @validating employee by uuid
     * @Validate if employee is empty otherwise return record not found
     * @Delete employee
     * @return a Success Message
     * * */
    public ApiResponse<String> deleteEmployee(UUID employeeId) {
//        if(jwtFilter.isAdmin()){
            Employee employee = validateEmployee(employeeId);
            employeeRepository.delete(employee);
            return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    "Record Deleted successfully");
        }

    @Override
    /**
     * @Validating existingEmployee by Email
     * @Validate employee password and change password
     * @Save the new password
     * @Return a Success Message if oldPassword is correct
     * @Return a Failed Message if oldPassword is Incorrect
     */
    public ApiResponse<String> resetPassword(String email, changeEmployeePasswordRequest request) {
        Employee employee = employeeRepository.findByEmail(email);

        if(employee.getPassword().equals(request.getOldPassword())){
            employee.setPassword(request.getNewPassword());
            employeeRepository.save(employee);

            return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    "Password Changed successfully");
        }
        return new ApiResponse(AppStatus.FAILED.label, HttpStatus.BAD_REQUEST.value(),
                "Incorrect Old Password");
    }


    @Override
    /**
     * @Finding Employee by Email
     * @Getting the value of email and password of employee and sending it to employee`s Email
     * @Return a Success Message
     */
    public ApiResponse<String> forgotEmployeePassword(String email) throws MessagingException {
        Employee employee = employeeRepository.findByEmail(email);

        emailUtils.forgotMail(employee.getEmail(), "Credentials by Hotel Management System", employee.getPassword() );

        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Check your email for credentials");
    }


    @Override
    /**
     * @Finding existingEmployee by Email
     * @Validate employee email and password
     * @Return a Success Message if email and password is correct
     * @Return a Failed Message if email and password is Incorrect
     */
    public ApiResponse<String> loginEmployee(String email, loginEmployeeRequest request) {
        Employee employee = employeeRepository.findByEmail(email);

        if (employee.getEmail().equals(request.getEmail())
                && employee.getPassword().equals(request.getPassword())) {

            return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    "Employee login successfully");
        }

        return new ApiResponse(AppStatus.FAILED.label, HttpStatus.BAD_REQUEST.value(),
                "Incorrect Email or Password");
    }


}
