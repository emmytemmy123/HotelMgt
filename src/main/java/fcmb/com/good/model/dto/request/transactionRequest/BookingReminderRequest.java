package fcmb.com.good.model.dto.request.transactionRequest;

import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static fcmb.com.good.utills.MessageUtil.INVALID_NAME;

@Data
public class BookingReminderRequest  {

       @NotNull(message = INVALID_NAME)
       @NotEmpty(message = INVALID_NAME)
       private Long customerId;

       @NotNull(message = INVALID_NAME)
       @NotEmpty(message = INVALID_NAME)
       private Long bookingId;

       @NotNull(message = INVALID_NAME)
       @NotEmpty(message = INVALID_NAME)
       private String reminderDetails;

       @NotNull(message = INVALID_NAME)
       @NotEmpty(message = INVALID_NAME)
       private String status;

}
