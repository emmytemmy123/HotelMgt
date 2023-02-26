package fcmb.com.good.model.dto.response.assetsResponse;

import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

import java.util.UUID;

@Data
public class DamagedAssetsResponse extends BaseDto {

     private UUID assetId;
     private UUID assetCategoryId;
     private String quantity;
     private String status;
     private String comment;
     private String name;
     private UUID createdBy;

}
