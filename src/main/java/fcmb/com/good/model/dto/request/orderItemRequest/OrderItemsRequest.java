package fcmb.com.good.model.dto.request.orderItemRequest;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static fcmb.com.good.utills.MessageUtil.INVALID_NAME;

@Data
public class OrderItemsRequest {

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String itemName;

    @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private Double salesPrice;

    @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private Integer quantity;

    @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private Double amount;

    @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private Double purchasePrice;

    @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private Double purchaseAmount;

    @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private String serviceName;

    @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private String description;


}
