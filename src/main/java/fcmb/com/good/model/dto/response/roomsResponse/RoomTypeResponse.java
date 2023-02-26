package fcmb.com.good.model.dto.response.roomsResponse;


import fcmb.com.good.model.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoomTypeResponse extends BaseDto {

     private String category;
     private String description;
     private Double cost;
     private String status;

}
