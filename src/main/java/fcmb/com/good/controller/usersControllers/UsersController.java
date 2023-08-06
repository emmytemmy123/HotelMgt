package fcmb.com.good.controller.usersControllers;

import fcmb.com.good.filter.JwtFilter;
import fcmb.com.good.model.dto.request.userRequest.*;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.userResponse.*;
import fcmb.com.good.services.others.UploadService;
import fcmb.com.good.services.user.*;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static fcmb.com.good.utills.EndpointParam.*;
import static fcmb.com.good.utills.EndPoints.UsersEndPoints.*;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(users)
@RestController
@RequiredArgsConstructor
public class UsersController  {

    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final EmployeeShiftService employeeShiftService;
    private final UserService userService;
 


                                        //FIND_LISTS_OF_USERS
    @GetMapping(FIND_CUSTOMER)
//    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    @ApiOperation(value = "Endpoint for retrieving lists of customer", response = CustomerResponse.class, responseContainer = "List")
    public ApiResponse<List<CustomerResponse>> getListOfCustomer(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                 @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return customerService.getListOfCustomer(page,size);
    }

    @GetMapping(FIND_EMPLOYEE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    @ApiOperation(value = "Endpoint for retrieving lists of employee", response = EmployeeResponse.class, responseContainer = "List")
    public ApiResponse<List<EmployeeResponse>> getListOfEmployee(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                           @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return employeeService.getListOfEmployee(page,size);
    }

    @GetMapping(FIND_EMPLOYEE_SHIFT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    @ApiOperation(value = "Endpoint for retrieving lists of employeeShift", response = EmployeeShiftResponse.class, responseContainer = "List")
    public ApiResponse<List<EmployeeShiftResponse>> getListOfEmployeeShift(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                           @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return employeeShiftService.getListOfEmployeeShift(page,size);
    }

    @GetMapping(FIND_USER)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    @ApiOperation(value = "Endpoint for retrieving lists of user", response = UserResponse.class, responseContainer = "List")
    public ApiResponse<List<UserResponse>> getListOfUsers(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                          @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return userService.getListOfUsers(page,size);
    }

//    @GetMapping(FIND_ROLE)
//    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
//    @ApiOperation(value = "Endpoint for retrieving lists of roles", response = RoleResponse.class, responseContainer = "List")
//    public ApiResponse<List<RoleResponse>> getListOfRoles(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
//                                                          @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
//        return roleService.getListOfRoles(page,size);
//    }
//
//    @GetMapping(FIND_USERTYPE)
//    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
//    @ApiOperation(value = "Endpoint for retrieving lists of usertype", response = UserTypeResponse.class, responseContainer = "List")
//    public ApiResponse<List<UserTypeResponse>> getListOfUserType(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
//                                                          @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
//        return userTypeService.getListOfUserType(page,size);
//    }



                                    //ADD_USERS
    @PostMapping(ADD_CUSTOMER)
    @ApiOperation(value = "Endpoint for adding new customer to database", response = String.class)
    public ApiResponse<String> addCustomer(@Valid @RequestBody CustomerRequest request) throws IOException {
        return customerService.addCustomer(request);
    }

    @PostMapping(ADD_EMPLOYEE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') ")
    @ApiOperation(value = "Endpoint for adding new employee to database", response = String.class)
    public ApiResponse<String> addEmployee(@Valid @RequestBody EmployeeRequest request) {
        return employeeService.addEmployee(request);
    }

    @PostMapping(ADD_EMPLOYEE_SHIFT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for adding new employeeShift to database", response = String.class)
    public ApiResponse<String> addEmployeeShift(@Valid @RequestBody EmployeeShiftRequest request) {
        return employeeShiftService.addEmployeeShift(request);
    }

    @PostMapping(ADD_USER)
    @ApiOperation(value = "Endpoint for adding new user to database", response = String.class)
    public ApiResponse<String> addUsers(@Valid @RequestBody UserRequest request) {
        return userService.addUsers(request);
    }

//    @PostMapping(ADD_ROLE)
//    @ApiOperation(value = "Endpoint for adding new role to database", response = String.class)
//    public ApiResponse<String> addRole(@Valid @RequestBody RoleRequest request) {
//        return roleService.addRole(request);
//    }
//
//    @PostMapping(ADD_USERTYPE)
//    @ApiOperation(value = "Endpoint for adding new usertype to database", response = String.class)
//    public ApiResponse<String> addUserType(@Valid @RequestBody UserTypeRequest request) {
//        return userTypeService.addUserType(request);
//    }

//    @PostMapping(ADD_IMAGE)
//    @ApiOperation(value = "Upload profile picture of User", response = String.class,
//            produces = "application/json", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ApiResponse uploadFile(@RequestPart(value = "file", required = true) MultipartFile file) throws IOException {
//        return uploadService.uploadFile(file);
//    }


                                         //FIND_USERS_BY_ID
    @GetMapping(FIND_CUSTOMER_BY_ID)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for fetching customer by id from database", response = CustomerResponse.class)
    public ApiResponse<CustomerResponse> getCustomerById(@PathVariable(value = "uuid") UUID customerUuid) {
        return customerService.getCustomerById(customerUuid);
    }

    @GetMapping(FIND_EMPLOYEE_BY_ID)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for fetching employee by id from database", response = EmployeeResponse.class)
    public ApiResponse<EmployeeResponse> getEmployeeById(@PathVariable(value = "uuid") UUID employeeUuid) {
        return employeeService.getEmployeeById(employeeUuid);
    }

    @GetMapping(FIND_EMPLOYEE_SHIFT_BY_ID)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for fetching employeeShift by id from database", response = EmployeeShiftResponse.class)
    public ApiResponse<EmployeeShiftResponse> getEmployeeShiftById(@PathVariable(value = "uuid") UUID employeeShiftUuid) {
        return employeeShiftService.getEmployeeShiftById(employeeShiftUuid);
    }

    @GetMapping(FIND_USER_BY_ID)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for fetching user by id from database", response = UserResponse.class)
    public ApiResponse<UserResponse> getUsersById(@PathVariable(value = "uuid") UUID userUuid) {
        return userService.getUsersById(userUuid);
    }


                                        //UPDATE_USERS
    @PutMapping(UPDATE_CUSTOMER)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') or hasAuthority('ROLE_USER') ")
    @ApiOperation(value = "Endpoint for updating customer by id from database", response = String.class)
    public ApiResponse<String> updateCustomer(@PathVariable(value = "uuid") UUID customerUuid,
                                              @RequestBody CustomerRequest request) {
        return customerService.updateCustomer(customerUuid, request);
    }


    @PutMapping(UPDATE_EMPLOYEE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for updating employee by id from database", response = String.class)
    public ApiResponse<String> updateEmployee(@PathVariable(value = "uuid") UUID employeeUuid,
                                              @RequestBody EmployeeRequest employee) {
        return employeeService.updateEmployee(employeeUuid, employee);
    }

    @PutMapping(UPDATE_EMPLOYEE_SHIFT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') ")
    @ApiOperation(value = "Endpoint for updating employeeShift by id from database", response = String.class)
    public ApiResponse<String> updateEmployeeShift(@PathVariable(value = "uuid") UUID employeeShiftUuid,
                                                   @RequestBody EmployeeShiftRequest request) {
        return employeeShiftService.updateEmployeeShift(employeeShiftUuid, request);
    }

    @PutMapping(UPDATE_USER)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') or hasAuthority('ROLE_USER') ")
    @ApiOperation(value = "Endpoint for updating user by id from database", response = String.class)
    public ApiResponse<String> updateUsers(@PathVariable(value = "uuid") UUID userUuid,
                                           @RequestBody UserRequest request) {
        return userService.updateUser(userUuid, request);
    }



                                            //DELETE_USERS

    @DeleteMapping(DELETE_EMPLOYEE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') ")
    @ApiOperation(value = "Endpoint for deleting employee by id from database", response = String.class)
    public ApiResponse<String> deleteEmployee(@PathVariable(value = "uuid") UUID employeeUuid) {
        return employeeService.deleteEmployee(employeeUuid);
    }

    @DeleteMapping(DELETE_EMPLOYEE_SHIFT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') ")
    @ApiOperation(value = "Endpoint for deleting employeeShift by id from database", response = String.class)
    public ApiResponse<String> deleteEmployeeShift(@PathVariable(value = "uuid") UUID employeeShiftUuid) {
        return employeeShiftService.deleteEmployeeShift(employeeShiftUuid);
    }

    @DeleteMapping(DELETE_USER)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') ")
    @ApiOperation(value = "Endpoint for deleting user by id from database", response = String.class)
    public ApiResponse<String> deleteUser(@PathVariable(value = "uuid") UUID userUuid) {
        return userService.deleteUser(userUuid);
    }



                                //Change Password

    @PutMapping(RESET_USER_PASSWORD)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') or hasAuthority('ROLE_USER') ")
    @ApiOperation(value = "Endpoint for resetting users password from database", response = String.class)
    public ApiResponse<String> resetPassword(@RequestBody changeUserPasswordRequest request, String email) {
        return userService.resetPassword(email, request);
    }

    @PutMapping(RESET_PASSWORD)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') or hasAuthority('ROLE_USER') ")
    @ApiOperation(value = "Endpoint for updating customer password from database", response = String.class)
    public ApiResponse<String> changeCustomerPassword(@RequestBody changeCustomerPasswordRequest request, String email) {
        return customerService.resetPassword(email, request);
    }

    @PutMapping(RESET_EMPLOYEE_PASSWORD)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') or hasAuthority('ROLE_USER') ")
    @ApiOperation(value = "Endpoint for resetting employee password from database", response = String.class)
    public ApiResponse<String> resetPassword(@RequestBody changeEmployeePasswordRequest request, String email) {
        return employeeService.resetPassword(email, request);
    }



                                        //Forgot Password
    @GetMapping(FORGOT_USER_PASSWORD)
    @PreAuthorize("hasAuthority('ROLE_USER') ")
    @ApiOperation(value = "Endpoint for getting forgotten users password from database", response = String.class)
    public ApiResponse<String> forgotUserPassword(String email) throws MessagingException {
        return userService.forgotUserPassword(email);
    }

    @GetMapping(FORGOT_CUSTOMER_PASSWORD)
    @PreAuthorize(" hasAuthority('ROLE_USER') ")
    @ApiOperation(value = "Endpoint for resetting customer password from database", response = String.class)
    public ApiResponse<String> forgotCustomerPassword(String email) throws MessagingException {
        return customerService.forgotCustomerPassword(email);
    }

    @GetMapping(FORGOT_EMPLOYEE_PASSWORD)
    @PreAuthorize("hasAuthority('ROLE_USER') ")
    @ApiOperation(value = "Endpoint for getting forgotten employee password from database", response = String.class)
    public ApiResponse<String> forgotEmployeePassword(String email) throws MessagingException {
        return employeeService.forgotEmployeePassword(email);
    }


                                        //login Users
    @PostMapping(LOGIN_USER)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER') ")
    @ApiOperation(value = "Endpoint to login users to database", response = String.class)
    public ApiResponse<String> loginUser(@RequestBody loginUserRequest request, String email) {
        return userService.loginUser(email, request);
    }

    @PostMapping(LOGIN_CUSTOMER)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER') ")
    @ApiOperation(value = "Endpoint to login customer to database", response = String.class)
    public ApiResponse<String> loginCustomer(@RequestBody loginCustomerRequest request, String email) {
        return customerService.loginCustomer(email, request);
    }

    @PostMapping(LOGIN_EMPLOYEE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER') ")
    @ApiOperation(value = "Endpoint to login employee to database", response = String.class)
    public ApiResponse<String> loginEmployee(@RequestBody loginEmployeeRequest request, String email) {
        return employeeService.loginEmployee(email, request);
    }



}
