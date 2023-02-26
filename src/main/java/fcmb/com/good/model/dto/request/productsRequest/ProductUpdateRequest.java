package fcmb.com.good.model.dto.request.productsRequest;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static fcmb.com.good.utills.MessageUtil.INVALID_NAME;

@Data
public class ProductUpdateRequest {

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String name;

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String description;

    @NotNull(message = INVALID_NAME)
    @Min(value = 1, message = INVALID_NAME)
    private Integer quantity;

    @NotNull(message = INVALID_NAME)
    @Min(value=50, message = INVALID_NAME)
    private Double price;

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private UUID category;

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String code;

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String location;

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String status;

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private UUID createdBy;

    @NotNull(message = INVALID_NAME)
    @Min(value=50, message = INVALID_NAME)
    private Double purchasedPrice;


}
