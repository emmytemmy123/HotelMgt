package fcmb.com.good.services.rooms;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.roomsRequest.RoomFacilityRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.roomsResponse.RoomFacilityResponse;
import fcmb.com.good.model.entity.rooms.RoomFacility;
import fcmb.com.good.model.entity.rooms.Rooms;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.model.entity.user.Customer;
import fcmb.com.good.repo.rooms.RoomFacilityRepository;
import fcmb.com.good.repo.rooms.RoomsRepository;
import fcmb.com.good.repo.user.CustomerRepository;
import fcmb.com.good.repo.user.UserRepository;
import fcmb.com.good.utills.MessageUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RoomFacilityServiceImpl implements RoomFacilityService {

    private final RoomFacilityRepository roomFacilityRepository;
    private final UserRepository userRepository;
    private final RoomsRepository roomRepository;
    private final CustomerRepository customerRepository;

    @Override
    /**
     * @Finding the list of roomFacility
     * @Validate the List of roomFacility is empty otherwise return record not found
     * @return the list of roomFacility and a Success Message
     * * */
    public ApiResponse<List<RoomFacilityResponse>> getListOfRoomFacility(int page, int size) {
        List<RoomFacility> roomFacilityList= roomFacilityRepository.findAll(PageRequest.of(page,size)).toList();
        if(roomFacilityList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(roomFacilityList, RoomFacilityResponse.class));
    }


    /**
     * @Validating existingRoomFacilityOptional by name
     * @Validating existingRoomCategoryOptional by name
     * @Validate the List of existingRoomFacilityOptional otherwise return Duplicate Record
     * * */
    private void validateDuplicationRoomFacility(String name){
        Optional<RoomFacility> existingRoomFacilityOptional = roomFacilityRepository.findByName(name);

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
    public ApiResponse<String> addRoomFacility(RoomFacilityRequest request) {

        validateDuplicationRoomFacility(request.getName());

        AppUser existingUser  = userRepository.findByUuid(request.getCreatedById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Customer existingCustomer  = customerRepository.findByUuid(request.getCustomerId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Rooms existingRoom  = roomRepository.findByUuid(request.getExistingRoomId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        RoomFacility roomFacility = new RoomFacility();
        roomFacility.setName(request.getName());
        roomFacility.setFileName(request.getFileName());
        roomFacility.setDescription(request.getDescription());
        roomFacility.setCreatedBy(existingUser);
        roomFacility.setExistingRoom(existingRoom);
        roomFacility.setCustomer(existingCustomer);

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
    public ApiResponse<RoomFacilityResponse> getRoomFacilityById(UUID roomFacilityId) {
        Optional<RoomFacility> roomFacilityOptional = roomFacilityRepository.findByUuid(roomFacilityId);

        if(roomFacilityOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        RoomFacility roomFacility = roomFacilityOptional.get();

        return new ApiResponse<RoomFacilityResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(roomFacility,RoomFacilityResponse.class));
    }


    /**
     * @validating roomFacility by uuid
     * @Validate if the List of roomFacility is empty otherwise return record not found
     * @return the list of roomFacility
     * * */
    private RoomFacility validateRoomFacility(UUID uuid){
        Optional<RoomFacility> roomFacility = roomFacilityRepository.findByUuid(uuid);
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
    public ApiResponse<String> updateRoomFacility(UUID roomFacilityId, RoomFacilityRequest request) {

        RoomFacility roomFacility = validateRoomFacility(roomFacilityId);
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
        RoomFacility roomFacility = validateRoomFacility(roomFacilityId);
        roomFacilityRepository.delete(roomFacility);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");    }


    @Override
    public ApiResponse<List<RoomFacilityResponse>> getRoomFacilityByRoomNumberAndCustomer(UUID roomUuid, UUID customerUuid) {

    List<RoomFacility> getRoomFacilityByRoomNumber = roomFacilityRepository.findRoomFacilityByRoomNumberAndCustomer(roomUuid, customerUuid);

    if(getRoomFacilityByRoomNumber.isEmpty())
        throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(getRoomFacilityByRoomNumber, RoomFacilityResponse.class));

    }

    @Override
    public ApiResponse<List<RoomFacilityResponse>> searchRoomFacilityByName(String name) {

        List<RoomFacility> searchRoomFacilityByName = roomFacilityRepository.searchRoomFacilityByName(name);

        if(searchRoomFacilityByName.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(searchRoomFacilityByName, RoomFacilityResponse.class));
    }


}
