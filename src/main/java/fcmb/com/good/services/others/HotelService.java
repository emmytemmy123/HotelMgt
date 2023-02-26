package fcmb.com.good.services.others;

import fcmb.com.good.model.dto.request.othersRequest.HotelRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.othersResponse.HotelResponse;
import fcmb.com.good.model.entity.others.Hotel;

import java.util.List;
import java.util.UUID;

public interface HotelService {

    ApiResponse<List<HotelResponse>> getListOfHotel(int page, int size);

    ApiResponse<String> addHotel(HotelRequest request);

    ApiResponse<HotelResponse> getHotelById(UUID hotelId);

    ApiResponse<String> updateHotel(UUID hotelId, HotelRequest request);

    ApiResponse<String> deleteHotel(UUID hotelId);




}
