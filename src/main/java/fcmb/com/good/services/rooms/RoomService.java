package fcmb.com.good.services.rooms;

import fcmb.com.good.model.dto.request.roomsRequest.RoomRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.roomsResponse.RoomResponse;

import java.util.List;
import java.util.UUID;

public interface RoomService {


    ApiResponse<List<RoomResponse>> getListOfRoom(int page, int size);

    ApiResponse<String> addRoom(RoomRequest request);

    ApiResponse<RoomResponse>getRoomById(UUID roomId);

    ApiResponse<String> updateRoom( UUID roomId, RoomRequest request);

    ApiResponse<String> deleteRoom(UUID roomId);

    ApiResponse<List<RoomResponse>> searchSubServiceByRoomId(Integer serviceNumber);





}
