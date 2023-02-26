package fcmb.com.good.model.dto.response.roomsResponse;


import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static fcmb.com.good.utills.MessageUtil.INVALID_NAME;

@Data
public class RoomResponse extends BaseDto {

     private String serviceType;
     private Integer serviceNumber;
     private String description;
     private String status;
     private Integer maxNoOccupant;


}
