package fcmb.com.good.model.dto.response.roomsResponse;


import fcmb.com.good.model.dto.response.userResponse.CustomerHelper;
import lombok.Data;

import java.util.UUID;

@Data
public class RoomHelper  {

    private UUID uuid;
//    private String serviceType;
    private String description;
    private Double price;

    private CustomerHelper currentOccupant;


}
