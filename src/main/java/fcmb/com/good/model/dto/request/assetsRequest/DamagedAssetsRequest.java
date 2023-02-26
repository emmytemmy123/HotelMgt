package fcmb.com.good.model.dto.request.assetsRequest;

import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.UUID;

import static fcmb.com.good.utills.MessageUtil.INVALID_NAME;

@Data
public class DamagedAssetsRequest  {

     @NotNull(message = INVALID_NAME)
     @NotEmpty(message = INVALID_NAME)
     private String name;

     @NotNull(message = INVALID_NAME)
     @NotEmpty(message = INVALID_NAME)
     private String quantity;

     @NotNull(message = INVALID_NAME)
     @NotEmpty(message = INVALID_NAME)
     private String status;

     @NotNull(message = INVALID_NAME)
     @NotEmpty(message = INVALID_NAME)
     private String comment;

     @NotNull(message = INVALID_NAME)
//     @NotEmpty(message = INVALID_NAME)
     private UUID createdById;

     @NotNull(message = INVALID_NAME)
     private UUID assetsCategoryId;

     @NotNull(message = INVALID_NAME)
     private UUID roomId;

     @NotNull(message = INVALID_NAME)
     private UUID assetId;

}
