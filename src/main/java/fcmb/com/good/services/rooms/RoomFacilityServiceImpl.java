package fcmb.com.good.services.rooms;

import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.RoomFacilityrequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.roomsResponse.RoomResponse;
import fcmb.com.good.model.entity.rooms.RoomFacility;
import fcmb.com.good.repo.rooms.RoomFacilityRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoomFacilityServiceImpl implements RoomFacilityService{
    private final RoomFacilityRepo roomFacilityRepo;

    @Override
    public ResponseEntity addRoomFacility(RoomFacilityrequest payload) {
       RoomFacility roomFacility = Mapper.convertObject(payload,RoomFacility.class);
       roomFacilityRepo.save(roomFacility);
       return ResponseEntity.ok(new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
               roomFacility));

    }

    @Override
    public ResponseEntity editRoomFacility(Long id, RoomFacilityrequest payload) {
RoomFacility roomFacility = roomFacilityRepo.findById(id).get();
if(Objects.nonNull(payload)){
    roomFacility = Mapper.convertObject(payload, RoomFacility.class);
}
return ResponseEntity.ok(new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
        roomFacility));

    }

    @Override
    public ResponseEntity deleteRoomFacility(Long roomId) {
     Optional  <RoomFacility> roomFacility = roomFacilityRepo.findById(roomId);
     if(roomFacility.isPresent()){
         roomFacilityRepo.delete(roomFacility.get());
     }
     return  ResponseEntity.ok(new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
            "Record successfully deleted "));
    }

    @Override
    public ResponseEntity listRoomFacility() {
        List<RoomFacility> roomFacilityList = roomFacilityRepo.findAll();
        if(roomFacilityList.isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                roomFacilityList));
    }

    @Override
    public ResponseEntity findRoomFacilityByRoomNumber(Long roomNumber) {
        List<RoomFacility> roomFacilityList = roomFacilityRepo.findRoomFacilitiesByRoom(roomNumber);

        return ResponseEntity.ok(new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                roomFacilityList));
    }
}
