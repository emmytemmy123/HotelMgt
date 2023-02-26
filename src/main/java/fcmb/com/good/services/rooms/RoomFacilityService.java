package fcmb.com.good.services.rooms;


import fcmb.com.good.model.dto.request.roomsRequest.RoomFacilityRequest;
import fcmb.com.good.model.dto.request.roomsRequest.RoomRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.roomsResponse.RoomFacilityResponse;
import fcmb.com.good.model.dto.response.roomsResponse.RoomResponse;

import java.util.List;
import java.util.UUID;

public interface RoomFacilityService {


    ApiResponse<String> addRoomFacility(RoomFacilityRequest request);

    ApiResponse<RoomFacilityResponse>getRoomFacilityById(UUID roomFacilityId);

    ApiResponse<String> updateRoomFacility( UUID roomFacilityId, RoomFacilityRequest request);

    ApiResponse<String> deleteRoomFacility(UUID roomFacilityId);

    ApiResponse<List<RoomFacilityResponse>> getRoomFacilityByRoomNumber(UUID roomUuid);



}
