package fcmb.com.good.services.transaction;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.transactionRequest.MaintenanceRequest;
import fcmb.com.good.model.dto.response.transactionResponse.MaintenanceResponse;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.entity.transaction.ExpenseCategory;
import fcmb.com.good.model.entity.transaction.Expenses;
import fcmb.com.good.model.entity.transaction.Maintenance;
import fcmb.com.good.model.entity.transaction.MaintenanceCategory;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.model.entity.user.Employee;
import fcmb.com.good.repo.transaction.MaintenanceCategoryRepository;
import fcmb.com.good.repo.transaction.MaintenanceRepository;
import fcmb.com.good.repo.user.EmployeeRepository;
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
public class MaintenanceServiceImpl implements MaintenanceService {

    private  final MaintenanceRepository maintenanceRepository;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final MaintenanceCategoryRepository maintenanceCategoryRepository;


    @Override
    /**
     * @Validate and Find the list of  Maintenance
     * @Validate if the List of Maintenance is empty otherwise return record not found*
     * @return the list of Maintenance and a Success Message
     * * */
    public ApiResponse<List<MaintenanceResponse>> getListOfMaintenance(int page, int size) {
        List<Maintenance> maintenanceList = maintenanceRepository.findAll(PageRequest.of(page,size)).toList();
        if(maintenanceList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(maintenanceList, MaintenanceResponse.class));

    }


    /**
     * @Validating existingMaintenanceOptional by name
     * @Validating existingMaintenanceOptional by name
     * @Validate the List of existingMaintenance and existingMaintenanceOptional is present otherwise return Duplicate Record*
     * * */
    private void validateDuplicateMaintenance(String name){

        Optional<Maintenance> maintenanceOptional = maintenanceRepository.findByName(name);

        if(maintenanceOptional.isPresent() )
            throw new RecordNotFoundException("Duplicate record");
    }


    @Override
    /**
     * @Validate that no duplicate Maintenance is allowed
     * @Validate that Maintenance exists otherwise return record not found
     * Create the Maintenance definition and save
     * @return success message
     * * */
    public ApiResponse<String> addMaintenance(MaintenanceRequest request) {

        validateDuplicateMaintenance(request.getName());

        AppUser existingUser  = userRepository.findByUuid(request.getCreatedById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Employee existingEmployee  = employeeRepository.findByUuid(request.getCurrentEmployeeId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        MaintenanceCategory existingMaintenanceCategory  = maintenanceCategoryRepository.findByUuid(request.getCurrentMaintenanceCategoryId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Maintenance maintenance = new Maintenance();

        maintenance.setCategory(request.getCategory());
        maintenance.setName(request.getName());
        maintenance.setDescription(request.getDescription());
        maintenance.setComment(request.getComment());
        maintenance.setCost(request.getCost());
        maintenance.setStatus(request.getStatus());
        maintenance.setQuantity(request.getQuantity());
        maintenance.setAmount((request.getCost())*(request.getQuantity()));
        maintenance.setMaintainedBy(existingEmployee.getName());
        maintenance.setRequestedBy(existingEmployee.getName());
        maintenance.setMaintenanceCategory(existingMaintenanceCategory);
        maintenance.setCreatedBy(existingUser);
        maintenance.setEmployee(existingEmployee);

        maintenanceRepository.save(maintenance);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record created successfully");

    }



    @Override
    /**
     * @Validating and Finding the list of MaintenanceOptional by uuid
     * @Validate if the List of MaintenanceOptional is empty otherwise return record not found
     * Create the Maintenance definition and get the MaintenanceOptional by uuid
     * @return the list of MaintenanceOptional and a Success Message
     * * */
    public ApiResponse<MaintenanceResponse> getMaintenanceById(UUID maintenanceId) {
        Optional<Maintenance> maintenanceOptional = maintenanceRepository.findByUuid(maintenanceId);

        if(maintenanceOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        Maintenance maintenance = maintenanceOptional.get();

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(maintenance,MaintenanceResponse.class));

    }


    /**
     * @validating MaintenanceOptional by uuid
     * @Validate if the List of Maintenance is empty otherwise return record not found
     * @return MaintenanceOptional
     * * */
    private Maintenance validateMaintenance(UUID uuid){
        Optional<Maintenance> maintenanceOptional = maintenanceRepository.findByUuid(uuid);
        if(maintenanceOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return maintenanceOptional.get();
    }



    @Override
    /**
     * @validating MaintenanceOptional by uuid
     * @Validate if the List of MaintenanceOptional is empty otherwise return record not found
     * Create the Maintenance definition and update
     * @return a Success Message
     * * */
    public ApiResponse<String> updateMaintenance( UUID maintenanceId, MaintenanceRequest request) {

        Maintenance maintenance = validateMaintenance(maintenanceId);

        maintenance.setCategory(request.getCategory());
        maintenance.setName(request.getName());
        maintenance.setDescription(request.getDescription());
        maintenance.setComment(request.getComment());
        maintenance.setCost(request.getCost());
        maintenance.setStatus(request.getStatus());
        maintenance.setQuantity(request.getQuantity());
        maintenance.setAmount((request.getCost())*(request.getQuantity()));

        maintenanceRepository.save(maintenance);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Updated Successfully");
    }


    @Override
    /**
     * @validate Maintenance by uuid
     * @Validate if Maintenance is empty otherwise return record not found
     * @Delete Maintenance
     * @return a Success Message
     * * */
    public ApiResponse<String> deleteMaintenance(UUID maintenanceId) {
        Maintenance maintenance = validateMaintenance(maintenanceId);
        maintenanceRepository.delete(maintenance);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }


}
