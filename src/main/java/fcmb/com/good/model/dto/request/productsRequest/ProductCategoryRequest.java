package fcmb.com.good.model.dto.request.productsRequest;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.UUID;

import static fcmb.com.good.utills.MessageUtil.PRODUCT_NAME;

@Data
public class ProductCategoryRequest {

//    @NotNull(message = PRODUCT_NAME)
//    @NotEmpty(message = PRODUCT_NAME)
    private String room;

//    @NotNull(message = PRODUCT_NAME)
//    @NotEmpty(message = PRODUCT_NAME)
    private String items;

    @NotNull(message = PRODUCT_NAME)
    @NotEmpty(message = PRODUCT_NAME)
    private String description;

    @NotNull(message = PRODUCT_NAME)
//  @NotEmpty(message = PRODUCT_NAME)
    private UUID createdById;

}
