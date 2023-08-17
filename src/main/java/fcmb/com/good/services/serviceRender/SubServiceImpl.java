package fcmb.com.good.services.serviceRender;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.servicesRequest.SubServiceRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.servicesResponse.SubServiceResponse;
import fcmb.com.good.model.entity.services.ServiceCategory;
import fcmb.com.good.model.entity.user.Users;
import fcmb.com.good.repo.products.ProductRepository;
import fcmb.com.good.repo.services.SubServiceRepository;
import fcmb.com.good.repo.user.UsersRepository;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubServiceImpl implements SubServiceService {

    private final SubServiceRepository subServiceRepository;
    private final UsersRepository usersRepository;
    private final ProductRepository productRepository;


    @Override
    /**
     * @Finding the list of all subServices
     * @Validate if the List of subService is empty otherwise return record not found
     * @return the list of subServices and a Success Message
     * * */
    public ApiResponse<List<SubServiceResponse>> getListOfSubService(int page, int size) {

        List<ServiceCategory> serviceCategoryList = subServiceRepository.findAll(PageRequest.of(page,size)).toList();
        if(serviceCategoryList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(serviceCategoryList, SubServiceResponse.class));
    }


    @Override
    /**
     * @Validate that no duplicate subService is allowed
     * @Validate that user creating the subService exists, otherwise return user not found
     * Create the subService definition and save
     * @return success message
     * * */
    public ApiResponse<String> addSubService(SubServiceRequest request) {

        validateDuplicateSubService(request.getServiceName());

        Users existingUser  = usersRepository.findByUuid(request.getCreatedById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        ServiceCategory serviceCategory = new ServiceCategory();

        serviceCategory.setServiceName(request.getServiceName());
        serviceCategory.setUnitCost(request.getUnitCost());
        serviceCategory.setCreatedBy(existingUser);

        subServiceRepository.save(serviceCategory);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record created successfully");

    }


    /**
     * @Validating existingSubServiceOptional by ServiceName
     * @Validate if the List of existingSubServiceOptional is empty otherwise return Duplicate Record
     * * */
    private void validateDuplicateSubService(String serviceName){
        Optional<ServiceCategory> existingSubServiceOptional = subServiceRepository.findByName(serviceName);
        if(existingSubServiceOptional.isPresent())
            throw new RecordNotFoundException("Duplicate record");
    }


    @Override
    /**
     * @Finding the list of all subServiceOptional by uuid
     * @Validate if the List of subServiceOptional is empty otherwise return record not found
     * Create the subServiceOptional definition and get the product Optional by uuid
     * @return the list of subService and a Success Message
     * * */
    public ApiResponse<SubServiceResponse> getSubServiceById(UUID subServiceId) {

        Optional<ServiceCategory> subServiceOptional = subServiceRepository.findByUuid(subServiceId);
        if(subServiceOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        ServiceCategory serviceCategory = subServiceOptional.get();

        return new ApiResponse<SubServiceResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(serviceCategory,SubServiceResponse.class));

    }


    /**
     * @validating subService by uuid
     * @Validate if subService is empty otherwise return record not found
     * @return subService
     * * */
    private ServiceCategory validateSubService(UUID uuid){
        return subServiceRepository.findByUuid(uuid).orElseThrow(()->
                new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));
    }


    @Override
    /**
     * @Validating the list of existingSubService by uuid
     * @Validate if the List of existingSubService is empty otherwise return record not found
     * Create the ServiceCategory definition and save
     * @return a Success Message
     * * */
    public ApiResponse<String> updateSubService(UUID subServiceId, SubServiceRequest request) {

        ServiceCategory serviceCategory = validateSubService(subServiceId);

        serviceCategory.setServiceName(request.getServiceName());
        serviceCategory.setUnitCost(request.getUnitCost());

        subServiceRepository.save(serviceCategory);
        return new ApiResponse<String>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record updated successfully");

    }

    @Override
    /**
     * @validating subService by uuid
     * @Validate if subService is empty otherwise return record not found
     * @Delete subService
     * @return a Success Message
     * * */
    public ApiResponse<String> deleteSubService(UUID subServiceId) {

        ServiceCategory serviceCategory = validateSubService(subServiceId);
        subServiceRepository.delete(serviceCategory);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");

    }


    @Override
    public ApiResponse<List<SubServiceResponse>> searchSubServiceByName(String serviceName) {

        List<ServiceCategory> searchServiceCategoryByName = subServiceRepository.searchSubServiceByName(serviceName);

        if(searchServiceCategoryByName.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(searchServiceCategoryByName, SubServiceResponse.class));

    }



}
