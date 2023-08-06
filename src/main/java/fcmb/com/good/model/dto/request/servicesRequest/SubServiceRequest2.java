package fcmb.com.good.model.dto.request.servicesRequest;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.util.UUID;

import static fcmb.com.good.utills.MessageUtil.INVALID_NAME;

@Data
public class SubServiceRequest2 {

        @NotNull(message = INVALID_NAME)
        private UUID orderId;

        @NotNull(message = INVALID_NAME)
        private UUID subServiceId;

        @NotNull(message = INVALID_NAME)
        @Min(value = 1, message = INVALID_NAME)
        private Integer noOfOccupant;

}
