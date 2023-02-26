package fcmb.com.good.services.user;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.userRequest.EmployeeShiftRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.userResponse.EmployeeShiftResponse;
import fcmb.com.good.model.entity.user.EmployeeShift;
import fcmb.com.good.repo.user.EmployeeShiftRepository;
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
public class EmployeeShiftServiceImpl implements EmployeeShiftService {

    private  final EmployeeShiftRepository employeeShiftRepository;


    @Override
    /**
     * @Finding the list of EmployeeShift
     * @Validate if the List of EmployeeShift is empty otherwise return record not found
     * @return the list of EmployeeShift and a Success Message*
     * * */
    public ApiResponse<List<EmployeeShiftResponse>> getListOfEmployeeShift(int page, int size) {
        List<EmployeeShift> employeeShiftList = employeeShiftRepository.findAll(PageRequest.of(page,size)).toList();

        if(employeeShiftList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(employeeShiftList, EmployeeShiftResponse.class));

    }


    @Override
    /**
     * @Validate that no duplicate EmployeeShift is allowed*
     * @Validate that EmployeeShift exists otherwise return record not found*
     * Create EmployeeShift definition and save
     * @return success message
     * * */
    public ApiResponse<String> addEmployeeShift(@RequestBody EmployeeShiftRequest request) {
        EmployeeShift employeeShift = Mapper.convertObject(request,EmployeeShift.class);
        employeeShiftRepository.save(employeeShift);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record added Successfully");
    }


    @Override
    /**
     * @Finding the list of EmployeeShiftOptional by uuid*
     * @Validate if the List of EmployeeShiftOptional is empty otherwise return record not found
     * Create the EmployeeShift definition and get the customer
     * @return the list of EmployeeShift and a Success Message
     * * */
    public  ApiResponse<EmployeeShiftResponse> getEmployeeShiftById(@RequestParam("id") UUID employeeShiftId) {
        Optional<EmployeeShift> employeeShiftOptional = employeeShiftRepository.findByUuid(employeeShiftId);

        if(employeeShiftOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        EmployeeShift employeeShift = employeeShiftOptional.get();

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(employeeShift,EmployeeShiftResponse.class));

    }


    /**
     * @validating EmployeeShiftOptional by uuid
     * @Validate if the List of EmployeeShift is empty otherwise return record not found
     * @return EmployeeShiftOptional
     * * */
    private EmployeeShift validateEmployeeShift(UUID uuid){
        Optional<EmployeeShift> employeeShift = employeeShiftRepository.findByUuid(uuid);

        if(employeeShift.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return employeeShift.get();
    }

    @Override
    /**
     * @validating EmployeeShiftOptional by uuid
     * @Validate if the List of EmployeeShift is empty otherwise return record not found
     * Create the EmployeeShift definition and update
     * @return a Success Message
     * * */
    public ApiResponse<String> updateEmployeeShift(UUID employeeShiftId, @RequestBody EmployeeShiftRequest request) {
        EmployeeShift employeeShifty = validateEmployeeShift(employeeShiftId);
        employeeShifty.setDesignation(request.getDesignation());
        employeeShifty.setShift(request.getShift());
        employeeShifty.setPeriod(request.getPeriod());

        employeeShiftRepository.save(employeeShifty);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Update Successfully");
    }


    @Override
    /**
     * @validating EmployeeShift by uuid
     * @Validate if EmployeeShift is empty otherwise return record not found
     * @Delete EmployeeShift
     * @return a Success Message
     * * */
    public  ApiResponse<String> deleteEmployeeShift(UUID employeeShiftId) {
        EmployeeShift employeeShift = validateEmployeeShift(employeeShiftId);
        employeeShiftRepository.delete(employeeShift);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }


}
