package fcmb.com.good.model.dto.request.assetsRequest;


import fcmb.com.good.model.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.UUID;

import static fcmb.com.good.utills.MessageUtil.INVALID_CATEGORY_ID;
import static fcmb.com.good.utills.MessageUtil.INVALID_NAME;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AssetsRequest  {

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String name;

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String description;

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String code;

    @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private Integer quantity;

    @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private UUID createdById;

    @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private UUID assetsCategoryId;



}
