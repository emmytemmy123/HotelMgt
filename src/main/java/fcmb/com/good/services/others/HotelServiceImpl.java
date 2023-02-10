package fcmb.com.good.services.others;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.othersRequest.HotelRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.othersResponse.HotelResponse;
import fcmb.com.good.model.entity.others.Hotel;
import fcmb.com.good.repo.others.HotelRepository;
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
public class HotelServiceImpl implements HotelService {
    private  final HotelRepository hotelRepository;

    @Override
    public ApiResponse<List<HotelResponse>> getListOfHotel(int page, int size) {
        List<Hotel> hotelList = hotelRepository.findAll(PageRequest.of(page,size)).toList();
        if(hotelList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(hotelList, HotelResponse.class));
    }

    @Override
    public ApiResponse<String> addHotel(@RequestBody HotelRequest request) {
        Hotel hotel = Mapper.convertObject(request,Hotel.class);
        hotel=hotelRepository.save(hotel);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record added Successfully");
    }

    @Override
    public ApiResponse<HotelResponse> getHotelById(@RequestParam("id") UUID hotelId) {
        Optional<Hotel> hotel = hotelRepository.findByUuid(hotelId);

        if(hotel.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        Hotel ht = hotel.get();
        return new ApiResponse<HotelResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(), Mapper.convertObject(ht,HotelResponse.class));

    }

    private Hotel validateHotel(UUID uuid){
        Optional<Hotel> hotel = hotelRepository.findByUuid(uuid);
        if(hotel.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return hotel.get();
    }

    @Override
    public ApiResponse<String> updateHotel(UUID hotelId, @RequestBody HotelRequest request) {
        Hotel hotel = validateHotel(hotelId);
        hotel.setName(request.getName());
        hotel.setEmail(request.getEmail());
        hotel.setCountry(request.getCountry());
        hotel.setState(request.getState());
        hotel.setCity(request.getCity());
        hotel.setAddress(request.getAddress());
        hotel.setPhone(request.getPhone());

        hotel = hotelRepository.save(hotel);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Updated Successfully");
    }

    @Override
    public ApiResponse<String> deleteHotel(UUID hotelId) {
        Hotel hotel = validateHotel(hotelId);
        hotelRepository.delete(hotel);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }

}
