package fcmb.com.good.controller.usersControllers;

import fcmb.com.good.model.dto.request.userRequest.*;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.userResponse.*;
import fcmb.com.good.services.others.UploadService;
import fcmb.com.good.services.user.*;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static fcmb.com.good.utills.EndpointParam.*;
import static fcmb.com.good.utills.EndPoints.UsersEndPoints.*;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(USERS)
@RestController
@RequiredArgsConstructor
public class UsersController  {

    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final EmployeeShiftService employeeShiftService;
    private final UserService userService;
    private final RoleService roleService;
    private final UploadService uploadService;
    private final UserTypeService userTypeService;



    //FIND_LISTS_OF_USERS
    @GetMapping(FIND_CUSTOMER)
    @ApiOperation(value = "Endpoint for retrieving lists of customer", response = CustomerResponse.class, responseContainer = "List")
    public ApiResponse<List<CustomerResponse>> getListOfCustomer(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                 @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return customerService.getListOfCustomer(page,size);
    }

    @GetMapping(FIND_EMPLOYEE)
    @ApiOperation(value = "Endpoint for retrieving lists of employee", response = EmployeeResponse.class, responseContainer = "List")
    public ApiResponse<List<EmployeeResponse>> getListOfEmployee(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                           @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return employeeService.getListOfEmployee(page,size);
    }

    @GetMapping(FIND_EMPLOYEESHIFT)
    @ApiOperation(value = "Endpoint for retrieving lists of employeeShift", response = EmployeeShiftResponse.class, responseContainer = "List")
    public ApiResponse<List<EmployeeShiftResponse>> getListOfEmployeeShift(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                           @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return employeeShiftService.getListOfEmployeeShift(page,size);
    }

    @GetMapping(FIND_USER)
    @ApiOperation(value = "Endpoint for retrieving lists of user", response = UserResponse.class, responseContainer = "List")
    public ApiResponse<UserResponse> getListOfUsers(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                    @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return userService.getListOfUsers(page,size);
    }

    @GetMapping(FIND_ROLE)
    @ApiOperation(value = "Endpoint for retrieving lists of roles", response = RoleResponse.class, responseContainer = "List")
    public ApiResponse<List<RoleResponse>> getListOfRoles(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                          @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return roleService.getListOfRoles(page,size);
    }

    @GetMapping(FIND_USERTYPE)
    @ApiOperation(value = "Endpoint for retrieving lists of usertype", response = UserTypeResponse.class, responseContainer = "List")
    public ApiResponse<List<UserTypeResponse>> getListOfUserType(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                          @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return userTypeService.getListOfUserType(page,size);
    }



                                    //ADD_USERS
    @PostMapping(ADD_CUSTOMER)
    @ApiOperation(value = "Endpoint for adding new customer to database", response = String.class)
    public ApiResponse<CustomerResponse> addCustomer(@Valid @RequestBody CustomerRequest request) throws IOException {
        return customerService.addCustomer(request);
    }

    @PostMapping(ADD_EMPLOYEE)
    @ApiOperation(value = "Endpoint for adding new employee to database", response = String.class)
    public ApiResponse<EmployeeResponse> addEmployee(@Valid @RequestBody EmployeeRequest request) {
        return employeeService.addEmployee(request);
    }

    @PostMapping(ADD_EMPLOYEESHIFT)
    @ApiOperation(value = "Endpoint for adding new employeeShift to database", response = String.class)
    public ApiResponse<EmployeeShiftResponse> addEmployeeShift(@Valid @RequestBody EmployeeShiftRequest request) {
        return employeeShiftService.addEmployeeShift(request);
    }

    @PostMapping(ADD_USER)
    @ApiOperation(value = "Endpoint for adding new user to database", response = String.class)
    public ApiResponse<UserResponse> addUsers(@Valid @RequestBody UserRequest request) {
        return userService.addUsers(request);
    }

    @PostMapping(ADD_ROLE)
    @ApiOperation(value = "Endpoint for adding new role to database", response = String.class)
    public ApiResponse<RoleResponse> addRole(@Valid @RequestBody RoleRequest request) {
        return roleService.addRole(request);
    }

    @PostMapping(ADD_USERTYPE)
    @ApiOperation(value = "Endpoint for adding new usertype to database", response = String.class)
    public ApiResponse<UserTypeResponse> addUserType(@Valid @RequestBody UserTypeRequest request) {
        return userTypeService.addUserType(request);
    }

    @PostMapping(ADD_IMAGE)
    @ApiOperation(value = "Upload profile picture of the dealer", response = String.class,
            produces = "application/json", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadFile(@RequestPart(value = "file", required = true) MultipartFile file) throws IOException {
        return uploadService.uploadFile(file);
    }


                                         //FIND_USERS_BY_ID
    @GetMapping(FIND_CUSTOMER_BY_ID)
    @ApiOperation(value = "Endpoint for fetching customer by id from database", response = CustomerResponse.class)
    public ApiResponse<CustomerResponse> getCustomerById(@PathVariable(value = "id") UUID customer_id) {
        return customerService.getCustomerById(customer_id);
    }

    @GetMapping(FIND_EMPLOYEE_BY_ID)
    @ApiOperation(value = "Endpoint for fetching employee by id from database", response = EmployeeResponse.class)
    public ApiResponse<EmployeeResponse> getEmployeeById(@PathVariable(value = "id") UUID employee_id) {
        return employeeService.getEmployeeById(employee_id);
    }

    @GetMapping(FIND_EMPLOYEESHIFT_BY_ID)
    @ApiOperation(value = "Endpoint for fetching employeeShift by id from database", response = EmployeeShiftResponse.class)
    public ApiResponse<EmployeeShiftResponse> getEmployeeShiftById(@PathVariable(value = "id") UUID employeeShift_id) {
        return employeeShiftService.getEmployeeShiftById(employeeShift_id);
    }

    @GetMapping(FIND_USER_BY_ID)
    @ApiOperation(value = "Endpoint for fetching user by id from database", response = UserResponse.class)
    public ApiResponse<UserResponse> getUsersById(@PathVariable(value = "id") UUID user_id) {
        return userService.getUsersById(user_id);
    }

    @GetMapping(FIND_ROLE_BY_ID)
    @ApiOperation(value = "Endpoint for fetching role by id from database", response = RoleResponse.class)
    public ApiResponse<RoleResponse> getRolesById(@PathVariable(value = "id") UUID role_id) {
        return roleService.getRolesById(role_id);
    }

    @GetMapping(FIND_USERTYPE_BY_ID)
    @ApiOperation(value = "Endpoint for fetching usertype by id from database", response = UserTypeResponse.class)
    public ApiResponse<UserTypeResponse> getUsertypeById(@PathVariable(value = "id") UUID userType_id) {
        return userTypeService.getUserTypeById(userType_id);
    }


                                        //UPDATE_USERS
    @PutMapping(UPDATE_CUSTOMER)
    @ApiOperation(value = "Endpoint for updating customer by id from database", response = String.class)
    public ApiResponse<CustomerResponse> updateCustomer(@PathVariable(value = "id") UUID customer_id,
                                                       @RequestBody CustomerRequest cus) {
        return customerService.updateCustomer(customer_id, cus);
    }

    @PutMapping(UPDATE_EMPLOYEE)
    @ApiOperation(value = "Endpoint for updating employee by id from database", response = String.class)
    public ApiResponse<EmployeeResponse> updateEmployee(@PathVariable(value = "id") UUID employee_id,
                                                        @RequestBody EmployeeRequest employee) {
        return employeeService.updateEmployee(employee_id, employee);
    }

    @PutMapping(UPDATE_EMPLOYEESHIFT)
    @ApiOperation(value = "Endpoint for updating employeeShift by id from database", response = String.class)
    public ApiResponse<EmployeeShiftResponse> updateEmployeeShift(@PathVariable(value = "id") UUID employeeShift_id,
                                                                  @RequestBody EmployeeShiftRequest request) {
        return employeeShiftService.updateEmployeeShift(employeeShift_id, request);
    }

    @PutMapping(UPDATE_USER)
    @ApiOperation(value = "Endpoint for updating user by id from database", response = String.class)
    public ApiResponse<UserResponse> updateUsers(@PathVariable(value = "id") UUID user_id,
                                                 @RequestBody UserRequest request) {
        return userService.updateUser(user_id, request);
    }

    @PutMapping(UPDATE_USERTYPE)
    @ApiOperation(value = "Endpoint for updating usertype by id from database", response = String.class)
    public ApiResponse<UserTypeResponse> updateUserType(@PathVariable(value = "id") UUID userType_id,
                                                @RequestBody UserTypeRequest request) {
        return userTypeService.updateUserType(userType_id, request);
    }

    @PutMapping(UPDATE_ROLE)
    @ApiOperation(value = "Endpoint for updating roles by id from database", response = String.class)
    public ApiResponse<RoleResponse> updateRole(@PathVariable(value = "id") UUID role_id,
                                                @RequestBody RoleRequest request) {
        return roleService.updateRole(role_id, request);
    }


                                            //DELETE_USERS
    @DeleteMapping(DELETE_CUSTOMER)
    @ApiOperation(value = "Endpoint for deleting customer by id from database", response = String.class)
    public ApiResponse<String> deleteCustomer(@PathVariable(value = "id") UUID customer_id) {
        return customerService.deleteCustomer(customer_id);
    }

    @DeleteMapping(DELETE_EMPLOYEE)
    @ApiOperation(value = "Endpoint for deleting employee by id from database", response = String.class)
    public ApiResponse<String> deleteEmployee(@PathVariable(value = "id") UUID employee_id) {
        return employeeService.deleteEmployee(employee_id);
    }

    @DeleteMapping(DELETE_EMPLOYEESHIFT)
    @ApiOperation(value = "Endpoint for deleting employeeShift by id from database", response = String.class)
    public ApiResponse<EmployeeShiftResponse> deleteEmployeeShift(@PathVariable(value = "id") UUID employeeShift_id) {
        return employeeShiftService.deleteEmployeeShift(employeeShift_id);
    }

    @DeleteMapping(DELETE_USER)
    @ApiOperation(value = "Endpoint for deleting user by id from database", response = String.class)
    public ApiResponse<String> deleteUser(@PathVariable(value = "id") UUID user_id) {
        return userService.deleteUser(user_id);
    }

    @DeleteMapping(DELETE_ROLE)
    @ApiOperation(value = "Endpoint for deleting role by id from database", response = String.class)
    public ApiResponse<String> deleteRole(@PathVariable(value = "id") UUID role_id) {
        return roleService.deleteRole(role_id);
    }

    @DeleteMapping(DELETE_USERTYPE)
    @ApiOperation(value = "Endpoint for deleting usertype by id from database", response = String.class)
    public ApiResponse<String> deleteUserType(@PathVariable(value = "id") UUID userType_id) {
        return userTypeService.deleteUserType(userType_id);
    }

    //change password
    @PutMapping(CHANGE_USER_PASSWORD)
    @ApiOperation(value = "Endpoint for updating users password from database", response = String.class)
    public ApiResponse<changeUserPasswordResponse> changeUserPassword(@RequestBody changeUserPasswordRequest request, String email) {
        return userService.changeUserPassword(email, request);
    }


    //Forgot Password
    @GetMapping(FORGOT_USER_PASSWORD)
    @ApiOperation(value = "Endpoint for getting forgotten users password from database", response = String.class)
    public ApiResponse<forgotUserPasswordResponse> forgotUserPassword(String email) throws MessagingException {
        return userService.forgotUserPassword(email);
    }


}
