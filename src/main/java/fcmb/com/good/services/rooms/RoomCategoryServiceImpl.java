package fcmb.com.good.services.rooms;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.roomsRequest.RoomTypeRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.roomsResponse.RoomTypeResponse;
import fcmb.com.good.model.entity.rooms.RoomCategory;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.repo.rooms.RoomCategoryRepository;
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
public class RoomCategoryServiceImpl implements RoomCategoryService {
    private  final RoomCategoryRepository roomCategoryRepository;
    private final UserRepository userRepository;

    @Override
    /**
     * @Validate and Find the list of  roomCategory
     * @Validate if the List of roomCategory is empty otherwise return record not found*
     * @return the list of roomCategory and a Success Message* *
     * * */
    public ApiResponse<List<RoomTypeResponse>> getListOfRoomType(int page, int size) {
        List<RoomCategory> roomTypeList = roomCategoryRepository.findAll(PageRequest.of(page,size)).toList();
        if(roomTypeList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(roomTypeList, RoomTypeResponse.class));
    }

    @Override
    /**
     * @Validate that no duplicate roomCategory is allowed
     * @Validate that room category exists otherwise return record not found
     * Create the room definition and save
     * @return success message
     * * */
    public ApiResponse<String> addRoomType( RoomTypeRequest request) {

        Optional<RoomCategory> roomCategoryOptional = validateDuplicateRoomType(request.getCategory());

        AppUser existingUser  = userRepository.findByUuid(request.getCreatedById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        if (!roomCategoryOptional.isEmpty()) {
            return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
                    "Duplicate Record");
        }

        RoomCategory roomType = new RoomCategory();
        roomType.setCategory(request.getCategory());
        roomType.setDescription(request.getDescription());
        roomType.setCost(request.getCost());
        roomType.setStatus(request.getStatus());
        roomType.setCreatedBy(existingUser);

        roomCategoryRepository.save(roomType);
        //roomCategoryRepository.save(getRoomTypeFromRequest(request));

        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Added successfully");
    }

//    /**
//     * Set and get the roomCategory parameters
//     */
//    private RoomCategory getRoomTypeFromRequest(RoomTypeRequest request) {
//        AppUser existingUser  = userRepository.findByUuid(request.getCreatedById())
//                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));
//
//        RoomCategory roomType = new RoomCategory();
//        roomType.setCategory(request.getCategory());
//        roomType.setDescription(request.getDescription());
//        roomType.setCost(request.getCost());
//        roomType.setStatus(request.getStatus());
//        roomType.setCreatedBy(existingUser);
//
//        return roomType;
//    }

    /**
     * @Validating existingRoomCategoryOption by category
     * @Validate if the List of existingRoomCategoryOption is empty otherwise return Duplicate Record
     * */
    private Optional<RoomCategory> validateDuplicateRoomType(String category) {
        Optional<RoomCategory> existingRoomCategoryOptional = roomCategoryRepository.findByRoomType(category);
        System.out.println(existingRoomCategoryOptional);
        return existingRoomCategoryOptional;
    }


//    @Override
//    /**
//     * @Validating and Finding the list of roomCategoryOptional by uuid
//     * @Validate if the List of roomCategoryOptional is empty otherwise return record not found
//     * Create the roomCategory definition and get the roomCategoryOptional by uuid
//     * @return the list of roomCategory and a Success Message
//     * * */
//    public ApiResponse<RoomTypeResponse> getRoomTypeById( UUID roomTypeId) {
//        Optional<RoomCategory> roomCategoryOptional = roomCategoryRepository.findByUuid(roomTypeId);
//
//        if(roomCategoryOptional.isEmpty())
//            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
//
//        RoomCategory roomCategory = roomCategoryOptional.get();
//        return new ApiResponse<RoomTypeResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
//                Mapper.convertObject(roomCategory,RoomTypeResponse.class));
//
//    }


    /**
     * @validating roomCategoryOptional by uuid
     * @Validate if the List of roomCategory is empty otherwise return record not found
     * @return roomCategoryOptional
     * * */
    private RoomCategory validateRoomType(UUID uuid){
        Optional<RoomCategory> roomCategoryOptional = roomCategoryRepository.findByUuid(uuid);
        if(roomCategoryOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return roomCategoryOptional.get();
    }

    @Override
    /**
     * @validating roomCategoryOptional by uuid
     * @Validate if the List of roomCategoryOptional is empty otherwise return record not found
     * Create the roomCategory definition and update
     * @return a Success Message
     * * */
    public ApiResponse<String> updateRoomType(UUID roomTypeId, RoomTypeRequest request) {

        RoomCategory roomCategory = validateRoomType(roomTypeId);

        roomCategory.setCategory(request.getCategory());
        roomCategory.setDescription(request.getDescription());
        roomCategory.setCost(request.getCost());
        roomCategory.setStatus(request.getStatus());

        roomCategoryRepository.save(roomCategory);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Updated Successfully");
    }


    @Override
    /**
     * @validate roomCategory by uuid
     * @Validate if roomCategory is empty otherwise return record not found
     * @Delete roomCategory
     * @return a Success Message
     * * */
    public ApiResponse<String> deleteRoomType(UUID roomTypeId) {
        RoomCategory roomType = validateRoomType(roomTypeId);
        roomCategoryRepository.delete(roomType);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }


}
