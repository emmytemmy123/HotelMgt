package fcmb.com.good.services.rooms;

import fcmb.com.good.model.dto.request.RoomFacilityrequest;
import org.springframework.http.ResponseEntity;

public interface RoomFacilityService {
    ResponseEntity addRoomFacility(RoomFacilityrequest payload);
    ResponseEntity editRoomFacility(Long id, RoomFacilityrequest payload);
    ResponseEntity deleteRoomFacility(Long roomId);
    ResponseEntity listRoomFacility();
    ResponseEntity findRoomFacilityByRoomNumber(Long roomNumber);
}
