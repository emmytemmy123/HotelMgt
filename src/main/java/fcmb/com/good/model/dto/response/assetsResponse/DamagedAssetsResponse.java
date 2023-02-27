package fcmb.com.good.model.dto.response.assetsResponse;

import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

import java.util.UUID;

@Data
public class DamagedAssetsResponse extends BaseDto {

     private String name;
     private String quantity;
     private String status;
     private String comment;

}
