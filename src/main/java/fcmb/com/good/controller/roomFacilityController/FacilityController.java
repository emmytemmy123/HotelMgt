package fcmb.com.good.controller.roomFacilityController;

import fcmb.com.good.model.dto.request.RoomFacilityrequest;
import fcmb.com.good.services.rooms.RoomFacilityService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("room_facility")
//@Api(tags = "CARD", consumes = "application/json", produces = "application/json",
//        protocols = "http https", value = "CARD")
@RequiredArgsConstructor
public class FacilityController {
    private final RoomFacilityService rooomFacilityService;
    @PostMapping("/add")
    ResponseEntity addFacility(@RequestBody RoomFacilityrequest payload){
        return rooomFacilityService.addRoomFacility(payload);
    }
    @PostMapping("/greet")
    ResponseEntity greetMe(){
        return ResponseEntity.ok("Good morning ");
    }
    @PostMapping("/edit")
    ResponseEntity editFacility(@RequestParam Long id,@RequestBody RoomFacilityrequest payload){
        return rooomFacilityService.editRoomFacility(id, payload);
    }
    @DeleteMapping("/delete")
    ResponseEntity deleteFacility(@RequestParam Long id){
        return rooomFacilityService.deleteRoomFacility(id);
    }
    @GetMapping("/list")
    ResponseEntity listFacility(){
        return rooomFacilityService.listRoomFacility();
    }
    @GetMapping("/list_by_room")
    ResponseEntity listFacilityByRoomId(@RequestParam Long id){
        return rooomFacilityService.findRoomFacilityByRoomNumber(id);
    }
}
