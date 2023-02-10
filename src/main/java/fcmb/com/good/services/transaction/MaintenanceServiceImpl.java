package fcmb.com.good.services.transaction;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.transactionRequest.MaintenanceRequestRequest;
import fcmb.com.good.model.dto.response.transactionResponse.MaintenanceResponse;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.entity.transaction.MaintenanceRequest;
import fcmb.com.good.repo.transaction.MaintenanceRequestRepository;
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
    private  final MaintenanceRequestRepository maintenanceRequestRepository;

    @Override
    public ApiResponse<List<MaintenanceResponse>> getListOfMaintenanceRequest(int page, int size) {
        List<MaintenanceRequest> maintenanceRequestList = maintenanceRequestRepository.findAll(PageRequest.of(page,size)).toList();
        if(maintenanceRequestList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(maintenanceRequestList, MaintenanceResponse.class));

    }

    @Override
    public ApiResponse<String> addMaintenanceRequest(@RequestBody MaintenanceRequestRequest request) {
        MaintenanceRequest maintenanceRequest = Mapper.convertObject(request,MaintenanceRequest.class);
        maintenanceRequestRepository.save(maintenanceRequest);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record added Successfully");
    }

    @Override
    public ApiResponse<MaintenanceResponse> getMaintenanceRequestById(@RequestParam("id") UUID maintenanceId) {
        Optional<MaintenanceRequest> maintenanceRequest = maintenanceRequestRepository.findByUuid(maintenanceId);

        if(maintenanceRequest.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        MaintenanceRequest mr = maintenanceRequest.get();

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(mr,MaintenanceResponse.class));

    }

    private MaintenanceRequest validateMaintenanceRequest(UUID uuid){
        Optional<MaintenanceRequest> maintenanceRequest = maintenanceRequestRepository.findByUuid(uuid);
        if(maintenanceRequest.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return maintenanceRequest.get();
    }

    @Override
    public ApiResponse<String> updateMaintenanceRequest(@RequestParam UUID maintenanceId,
                                                                     @RequestBody MaintenanceRequestRequest request) {
        MaintenanceRequest maintenanceRequest = validateMaintenanceRequest(maintenanceId);
        maintenanceRequest.setMaintenance_category(request.getMaintenance_category());
        maintenanceRequest.setAsset_id(request.getAsset_id());
        maintenanceRequest.setComment(request.getComment());
        maintenanceRequest.setQuantity(request.getQuantity());
        maintenanceRequest.setCost(request.getCost());
        maintenanceRequest.setStatus(request.getStatus());
        maintenanceRequest.setRequested_by(request.getRequested_by());
        maintenanceRequest.setMaintained_by(request.getMaintained_by());

        maintenanceRequestRepository.save(maintenanceRequest);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Updated Successfully");
    }

    @Override
    public ApiResponse<String> deleteMaintenanceRequest(UUID maintenanceId) {
        MaintenanceRequest maintenanceRequest = validateMaintenanceRequest(maintenanceId);
        maintenanceRequestRepository.delete(maintenanceRequest);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }


}
