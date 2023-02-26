package fcmb.com.good.model.dto.request.kitchen;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.UUID;

import static fcmb.com.good.utills.MessageUtil.INVALID_NAME;

@Data
public class KitchenCategoryRequest {

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String categoryName;

    @NotNull(message = INVALID_NAME)
    private UUID createdById;


}
