package fcmb.com.good.services.user;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.filter.JwtFilter;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.userRequest.EmployeeRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.userResponse.CustomerResponse;
import fcmb.com.good.model.dto.response.userResponse.EmployeeResponse;
import fcmb.com.good.model.entity.user.Customer;
import fcmb.com.good.model.entity.user.Employee;
import fcmb.com.good.repo.user.EmployeeRepository;
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
public class EmployeeServiceImpl implements EmployeeService {

    private  final EmployeeRepository employeeRepository;

    private final JwtFilter jwtFilter;


    @Override
    public ApiResponse<List<EmployeeResponse>> getListOfEmployee(int page, int size) {
        if(jwtFilter.isAdmin()){
            List<Employee> employeeList = employeeRepository.findAll(PageRequest.of(page,size)).toList();
            if(employeeList.isEmpty())
                throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

            return new ApiResponse<>(AppStatus.SUCCESS.label,
                    HttpStatus.OK.value(),
                    Mapper.convertList(employeeList, EmployeeResponse.class));
        }
        return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
                "You are not Authorized");
    }


    @Override
    public ApiResponse<EmployeeResponse> addEmployee(@RequestBody EmployeeRequest request) {
        if(jwtFilter.isAdmin()){
            Optional<Employee> employee  = validateEmployeeByEmailId(request.getEmail());
            if(!employee.isEmpty()){
                return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
                        "Email Already Exist");
            }
            employeeRepository.save(getEmployeeFromRequest(request));
            return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    Mapper.convertObject(employee, EmployeeResponse.class));
        }
        return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
                "You are not Authorized");
    }


    @Override
    public ApiResponse<EmployeeResponse> getEmployeeById(@RequestParam("id") UUID employeeId) {
        Optional<Employee> employee = employeeRepository.findByUuid(employeeId);

        if(employee.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        Employee cm = employee.get();
        return new ApiResponse<EmployeeResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(cm,EmployeeResponse.class));

    }

    private Optional<Employee> validateEmployeeByEmailId(String email){
        Optional<Employee> employee = employeeRepository.findByEmailId(email);
        System.out.println(employee);
        return employee;
    }

    private Employee validateEmployee(UUID uuid){
        Optional<Employee> employee = employeeRepository.findByUuid(uuid);

        if(employee.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return employee.get();
    }

    private Employee getEmployeeFromRequest(EmployeeRequest request){
        Employee emp = new Employee();
        emp.setPostedBy(request.getPostedBy());
        emp.setName(request.getName());
        emp.setEmail(request.getEmail());
        emp.setAddress(request.getAddress());
        emp.setCountry(request.getCountry());
        emp.setCity(request.getCity());
        emp.setGender(request.getGender());
        emp.setPassword(request.getPassword());
        emp.setPhone(request.getPhone());
        emp.setUsername(request.getUsername());
        emp.setPhoto(request.getPhoto());
        emp.setRole(request.getRole());
        emp.setDesignation(request.getDesignation());
        return emp;
    }


    @Override
    public ApiResponse<EmployeeResponse> updateEmployee(UUID employeeId, @RequestBody EmployeeRequest employee) {
        if(jwtFilter.isAdmin()){
            Employee employ = validateEmployee(employeeId);
            employ.setName(employee.getName());
            employ.setEmail(employee.getEmail());
            employ.setGender(employee.getGender());
            employ.setCountry(employee.getCountry());
            employ.setCity(employee.getCity());
            employ.setAddress(employee.getAddress());
            employ.setPhone(employee.getPhone());
            employ.setDesignation(employee.getDesignation());
            employ.setUsername(employee.getUsername());

            employ = employeeRepository.save(employ);
            return new ApiResponse<EmployeeResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    Mapper.convertObject(employ,EmployeeResponse.class));
        }
        return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
                "You are not Authorized");
    }


    @Override
    public ApiResponse<String> deleteEmployee(UUID employeeId) {
        if(jwtFilter.isAdmin()){
            Employee employee = validateEmployee(employeeId);
            employeeRepository.delete(employee);
            return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    "Record Deleted successfully");
        }
        return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
                "You are not Authorized");
    }


}
