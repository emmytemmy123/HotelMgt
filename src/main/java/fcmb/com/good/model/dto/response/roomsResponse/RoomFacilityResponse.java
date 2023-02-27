package fcmb.com.good.model.dto.response.roomsResponse;

import fcmb.com.good.model.dto.BaseDto;
import fcmb.com.good.model.dto.response.userResponse.CustomerHelper;
import lombok.Data;

@Data
public class RoomFacilityResponse extends BaseDto {

    private String name;

    private String fileName;

    private String description;

    private RoomHelper room;



}
