package fcmb.com.good.controller.roomsControllers;


import fcmb.com.good.model.dto.request.roomsRequest.RoomFacilityRequest;
import fcmb.com.good.model.dto.request.roomsRequest.RoomRequest;
import fcmb.com.good.model.dto.request.roomsRequest.RoomTypeRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.roomsResponse.RoomFacilityResponse;
import fcmb.com.good.model.dto.response.roomsResponse.RoomResponse;
import fcmb.com.good.model.dto.response.roomsResponse.RoomTypeResponse;
import fcmb.com.good.model.dto.response.servicesResponse.SubServiceResponse;
import fcmb.com.good.services.rooms.RoomFacilityService;
import fcmb.com.good.services.rooms.RoomService;
import fcmb.com.good.services.rooms.RoomCategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.UUID;

import static fcmb.com.good.utills.EndPoints.RoomEndPoints.*;
import static fcmb.com.good.utills.EndPoints.RoomEndPoints.USERS;
import static fcmb.com.good.utills.EndpointParam.*;
import static fcmb.com.good.utills.EndpointParam.SIZE_DEFAULT;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final RoomCategoryService roomTypeService;
    private final RoomFacilityService roomFacilityService;

                                    //FIND_LISTS_OF_ROOMS
    @GetMapping(FIND_ROOM)
    @ApiOperation(value = "Endpoint for retrieving lists of rooms", response = RoomResponse.class, responseContainer = "List")
    public ApiResponse<List<RoomResponse>> getListOfRoom(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                         @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return roomService.getListOfRoom(page,size);
    }

    @GetMapping(FIND_ROOM_TYPE)
    @ApiOperation(value = "Endpoint for retrieving lists of roomType", response = RoomTypeResponse.class, responseContainer = "List")
    public ApiResponse<List<RoomTypeResponse>> getListOfRoomType(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                 @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return roomTypeService.getListOfRoomType(page,size);
    }

    @GetMapping(FIND_ROOM_FACILITY)
    @ApiOperation(value = "Endpoint for retrieving lists of roomFacility", response = RoomFacilityResponse.class, responseContainer = "List")
    public ApiResponse<List<RoomFacilityResponse>> getListOfRoomFacility(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                         @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size) {
        return roomFacilityService.getListOfRoomFacility(page,size);
    }


                                     //ADD_ROOMS
    @PostMapping(ADD_ROOM)
    @ApiOperation(value = "Endpoint for adding new room to database", response = String.class)
    public ApiResponse<String> addRoom(@Valid @RequestBody RoomRequest request) {
        return roomService.addRoom(request);
    }

    @PostMapping(ADD_ROOM_TYPE)
    @ApiOperation(value = "Endpoint for adding new roomType to database", response = String.class)
    public ApiResponse<String> addRoomType(@Valid @RequestBody RoomTypeRequest request) {
        return roomTypeService.addRoomType(request);
    }

    @PostMapping(ADD_ROOM_FACILITY)
    @ApiOperation(value = "Endpoint for adding new roomFacility to database", response = String.class)
    public ApiResponse<String> addRoomFacility(@Valid @RequestBody RoomFacilityRequest request) {
        return roomFacilityService.addRoomFacility(request);
    }


                                        //FIND_ROOM_BY_ID
    @GetMapping(FIND_ROOM_BY_ID)
    @ApiOperation(value = "Endpoint for fetching rooms by id from database", response = RoomResponse.class)
    public ApiResponse<RoomResponse> getRoomById(@PathVariable(value = "id") UUID room_id) {
        return roomService.getRoomById(room_id);
    }

    @GetMapping(FIND_ROOM_TYPE_BY_ID)
    @ApiOperation(value = "Endpoint for fetching roomType by id from database", response = RoomTypeResponse.class)
    public ApiResponse<RoomTypeResponse> getRoomTypeById(@PathVariable(value = "id") UUID room_type_id) {
        return roomTypeService.getRoomTypeById(room_type_id);
    }

    @GetMapping(FIND_ROOM_FACILITY_BY_ID)
    @ApiOperation(value = "Endpoint for fetching roomFacility by id from database", response = RoomFacilityResponse.class)
    public ApiResponse<RoomFacilityResponse> getRoomFacilityById(@PathVariable(value = "id") UUID roomFacilityId) {
        return roomFacilityService.getRoomFacilityById(roomFacilityId);
    }


                                        //UPDATE_ROOM
    @PutMapping(UPDATE_ROOM)
    @ApiOperation(value = "Endpoint for updating room by id from database", response = String.class)
    public ApiResponse<String> updateRoom(@PathVariable(value = "id") UUID room_id, @RequestBody RoomRequest request) {
        return roomService.updateRoom(room_id, request);
    }

    @PutMapping(UPDATE_ROOM_TYPE)
    @ApiOperation(value = "Endpoint for updating roomType by id from database", response = String.class)
    public ApiResponse<String> updateRoomType(@PathVariable(value = "id") UUID roomType_id,
                                              @RequestBody RoomTypeRequest request) {
        return roomTypeService.updateRoomType(roomType_id, request);
    }

    @PutMapping(UPDATE_ROOM_FACILITY)
    @ApiOperation(value = "Endpoint for updating roomFacility by id from database", response = String.class)
    public ApiResponse<String> updateRoomFacility(@PathVariable(value = "id") UUID roomFacilityId,
                                              @RequestBody RoomFacilityRequest request) {
        return roomFacilityService.updateRoomFacility(roomFacilityId, request);
    }


                                        //DELETE ROOM
    @DeleteMapping(DELETE_ROOM)
    @ApiOperation(value = "Endpoint for deleting room by id from database", response = String.class)
    public ApiResponse<String> deleteRoom(@PathVariable(value = "id") UUID room_id) {
        return roomService.deleteRoom(room_id);
    }

    @DeleteMapping(DELETE_ROOM_TYPE)
    @ApiOperation(value = "Endpoint for deleting roomType by id from database", response = String.class)
    public ApiResponse<String> deleteRoomType(@PathVariable(value = "id") UUID roomType_id) {
        return roomTypeService.deleteRoomType(roomType_id);
    }

    @DeleteMapping(DELETE_ROOM_FACILITY)
    @ApiOperation(value = "Endpoint for deleting roomFacility by id from database", response = String.class)
    public ApiResponse<String> deleteRoomFacility(@PathVariable(value = "id") UUID roomFacilityId) {
        return roomFacilityService.deleteRoomFacility(roomFacilityId);
    }


                                        //FIND_SUB_SERVICE_BY_ROOM_ID




                                //FIND_ROOM_FACILITY_BY_ROOM_NUMBER_AND_CUSTOMER

    @GetMapping(SEARCH_ROOM_FACILITY_BY_ROOM_NUMBER_AND_CUSTOMER)
    @ApiOperation(value = "Endpoint for retrieving lists of roomFacility by RoomNumberAndCustomer", response = RoomFacilityResponse.class, responseContainer = "List")
    public ApiResponse<List<RoomFacilityResponse>> searchListOfRoomFacilityByRoomNumber(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                            @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size,
                                                                            @RequestParam UUID roomUuid, @RequestParam UUID customerUuid ) {
        return roomFacilityService.getRoomFacilityByRoomNumberAndCustomer(roomUuid, customerUuid);
    }


                            //FIND_ROOM_FACILITY_BY_NAME

    @GetMapping(SEARCH_ROOM_FACILITY_BY_NAME)
    @ApiOperation(value = "Endpoint for retrieving lists of roomFacility by name", response = RoomFacilityResponse.class, responseContainer = "List")
    public ApiResponse<List<RoomFacilityResponse>> searchListOfRoomFacilityByName(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                                        @RequestParam(value=SIZE,defaultValue=SIZE_DEFAULT) int size,
                                                                                        @RequestParam String name) {
        return roomFacilityService.searchRoomFacilityByName(name);
    }


}
