package fcmb.com.good.model.dto.request.assetsRequest;

import fcmb.com.good.model.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.UUID;

import static fcmb.com.good.utills.MessageUtil.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AssetsCategoryRequest  {

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String name;

    @NotNull(message = INVALID_DESCRIPTION)
    @NotEmpty(message = INVALID_DESCRIPTION)
    private String description;

    @NotNull(message = INVALID_ACCOUNT_NO)
//    @NotEmpty(message = INVALID_ACCOUNT_NO)
    private UUID createdById;

}
