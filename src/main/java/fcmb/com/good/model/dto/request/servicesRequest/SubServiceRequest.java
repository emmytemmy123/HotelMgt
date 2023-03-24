package fcmb.com.good.model.dto.request.servicesRequest;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.UUID;

import static fcmb.com.good.utills.MessageUtil.INVALID_NAME;

@Data
public class SubServiceRequest {

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String serviceName;

    @NotNull(message = INVALID_NAME)
    @Min(value = 50, message = INVALID_NAME)
    private Double unitCost;

    @NotNull(message = INVALID_NAME)
    @Min(value = 1, message = INVALID_NAME)
    private Integer noOfOccupant;

    @NotNull(message = INVALID_NAME)
    private UUID createdById;

    @NotNull(message = INVALID_NAME)
    private UUID customerId;

    @NotNull(message = INVALID_NAME)
    private UUID productId;


}
