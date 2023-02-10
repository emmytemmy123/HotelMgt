package fcmb.com.good.services.serviceRender;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.servicesRequest.ServicesRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.servicesResponse.ServiceResponse;
import fcmb.com.good.model.entity.services.Services;
import fcmb.com.good.repo.services.ServicesRepository;
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
public class ServiceServiceImpl implements ServiceService {
    private  final ServicesRepository servicesRepository;

    @Override
    public ApiResponse<List<ServiceResponse>> getListOfService(int page, int size) {
        List<Services> servicesList = servicesRepository.findAll(PageRequest.of(page,size)).toList();
        if(servicesList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(servicesList, ServiceResponse.class));
    }

    @Override
    public ApiResponse<String> addService(@RequestBody ServicesRequest request) {
        Services services = Mapper.convertObject(request,Services.class);
        servicesRepository.save(services);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record added Successfully");
    }

    @Override
    public ApiResponse<ServiceResponse> getServiceById(@RequestParam("id") UUID serviceId) {
        Optional<Services> services = servicesRepository.findByUuid(serviceId);
        if(services.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        Services se = services.get();
        return new ApiResponse<ServiceResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(), Mapper.convertObject(se,ServiceResponse.class));

    }

    private Services validateServices(UUID uuid){
        Optional<Services> services = servicesRepository.findByUuid(uuid);
        if(services.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return services.get();
    }

    @Override
    public ApiResponse<String> updateService(UUID serviceId, @RequestBody ServicesRequest request) {
        Services services = validateServices(serviceId);
        services.setService_type(request.getService_type());
        services.setDescription(request.getDescription());
        services.setService_category(request.getService_category());

        services = servicesRepository.save(services);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Updated Successfully");
    }

    @Override
    public ApiResponse<String>deleteService(UUID serviceId) {
        Services services = validateServices(serviceId);
        servicesRepository.delete(services);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }


}
