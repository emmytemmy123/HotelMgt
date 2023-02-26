package fcmb.com.good.services.rooms;

import fcmb.com.good.model.dto.request.roomsRequest.RoomTypeRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.roomsResponse.RoomTypeResponse;

import java.util.List;
import java.util.UUID;

public interface RoomCategoryService {

    ApiResponse<List<RoomTypeResponse>> getListOfRoomType(int page, int size);

    ApiResponse<String> addRoomType(RoomTypeRequest request);

//    ApiResponse<RoomTypeResponse> getRoomTypeById(UUID roomTypeId);

    ApiResponse<String> updateRoomType( UUID roomTypeId, RoomTypeRequest request);

    ApiResponse<String> deleteRoomType(UUID roomTypeId);


}
