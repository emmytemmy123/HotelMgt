package fcmb.com.good.services.products;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.productsRequest.ProductFacilityRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.productsResponse.ProductFacilityResponse;
import fcmb.com.good.model.entity.products.ProductFacility;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.repo.products.ProductFacilityRepository;
import fcmb.com.good.repo.user.CustomerRepository;
import fcmb.com.good.repo.user.UserRepository;
import fcmb.com.good.utills.MessageUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductFacilityServiceImpl implements ProductFacilityService {

    private final ProductFacilityRepository roomFacilityRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;



    /**
     * @Validating existingRoomFacilityOptional by name
     * @Validating existingRoomCategoryOptional by name
     * @Validate the List of existingRoomFacilityOptional otherwise return Duplicate Record
     * * */
    private void validateDuplicationRoomFacility(String name){
        Optional<ProductFacility> existingRoomFacilityOptional = roomFacilityRepository.findByName(name);

        if(existingRoomFacilityOptional.isPresent() )
            throw new RecordNotFoundException("Duplicate record");
    }

    @Override
    /**
     * @Validate that no duplicate roomFacilityName is allowed
     * @Validate that user creating the roomFacility exists, otherwise return user not found
     * Create the roomFacility definition and save
     * @return success message
     * * */
    public ApiResponse<String> addRoomFacility(ProductFacilityRequest request) {

        validateDuplicationRoomFacility(request.getName());

        AppUser existingUser  = userRepository.findByUuid(request.getCreatedById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));


        ProductFacility roomFacility = new ProductFacility();
        roomFacility.setName(request.getName());
        roomFacility.setFileName(request.getFileName());
        roomFacility.setDescription(request.getDescription());
        roomFacility.setCreatedBy(existingUser);

        roomFacilityRepository.save(roomFacility);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record created successfully");

    }


    @Override
    /**
     * @Finding the list of all roomFacilityOptional by uuid
     * @Validate if the List of roomFacilityOptional is empty otherwise return record not found
     * Create the roomFacility definition and get the roomFacilityOptional by uuid
     * @return the list of roomFacility and a Success Message
     * * */
    public ApiResponse<ProductFacilityResponse> getRoomFacilityById(UUID roomFacilityId) {
        Optional<ProductFacility> roomFacilityOptional = roomFacilityRepository.findByUuid(roomFacilityId);

        if(roomFacilityOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        ProductFacility roomFacility = roomFacilityOptional.get();

        return new ApiResponse<ProductFacilityResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(roomFacility, ProductFacilityResponse.class));
    }


    /**
     * @validating roomFacility by uuid
     * @Validate if the List of roomFacility is empty otherwise return record not found
     * @return the list of roomFacility
     * * */
    private ProductFacility validateRoomFacility(UUID uuid){
        Optional<ProductFacility> roomFacility = roomFacilityRepository.findByUuid(uuid);
        if(roomFacility.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return roomFacility.get();
    }


    @Override
    /**
     * @Validating the list of existingRoomFacility by uuid
     * @Validate if the List of existingRoomFacility is empty otherwise return record not found
     * Create the roomFacility definition and save
     * @return a Success Message
     * * */
    public ApiResponse<String> updateRoomFacility(UUID roomFacilityId, ProductFacilityRequest request) {

        ProductFacility roomFacility = validateRoomFacility(roomFacilityId);
        roomFacility.setName(request.getName());
        roomFacility.setFileName(request.getFileName());
        roomFacility.setDescription(request.getDescription());

        roomFacilityRepository.save(roomFacility);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Updated successfully");
    }

    @Override
    /**
     * @validating roomFacility by uuid
     * @Validate if roomFacility is empty otherwise return record not found
     * @Delete roomFacility
     * @return a Success Message
     * * */
    public ApiResponse<String> deleteRoomFacility(UUID roomFacilityId) {
        ProductFacility roomFacility = validateRoomFacility(roomFacilityId);
        roomFacilityRepository.delete(roomFacility);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }


    @Override
    /**
     * @Search the list of all RoomFacilityResponse by roomNo
     * @Validate if the List of RoomFacilityResponse is empty otherwise return record not found*
     * @return the list of RoomFacilityResponse by roomNo
     * * */
    public ApiResponse<List<ProductFacilityResponse>> getRoomFacilityByRoomNumber(UUID roomUuid) {

    List<ProductFacility> getRoomFacilityByRoomNumber = roomFacilityRepository.findRoomFacilityByRoomNumberAndCustomer(roomUuid);

    if(getRoomFacilityByRoomNumber.isEmpty())
        throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(getRoomFacilityByRoomNumber, ProductFacilityResponse.class));

    }



}