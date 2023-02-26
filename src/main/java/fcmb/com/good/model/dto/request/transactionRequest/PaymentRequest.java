package fcmb.com.good.model.dto.request.transactionRequest;

import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

import static fcmb.com.good.utills.MessageUtil.INVALID_NAME;

@Data
public class PaymentRequest  {


       @NotNull(message = INVALID_NAME)
       @NotEmpty(message = INVALID_NAME)
       private String paymentStatus;

       @NotNull(message = INVALID_NAME)
       @NotEmpty(message = INVALID_NAME)
       private String paymentDetails;

       @NotNull(message = INVALID_NAME)
//       @NotEmpty(message = INVALID_NAME)
       private Integer quantity;

       @NotNull(message = INVALID_NAME)
//       @NotEmpty(message = INVALID_NAME)
       private Integer accountNo;

       @NotNull(message = INVALID_NAME)
       private UUID createdById;

       @NotNull(message = INVALID_NAME)
       private UUID currentProductId;

       @NotNull(message = INVALID_NAME)
       private UUID currentRoomId;

       @NotNull(message = INVALID_NAME)
       private UUID currentKitchenId;

       @NotNull(message = INVALID_NAME)
       private UUID currentCustomerId;



}
