package fcmb.com.good.model.dto.request.transactionRequest;

import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

import static fcmb.com.good.utills.MessageUtil.INVALID_NAME;

@Data
public class BookingRequest  {

      @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
      private UUID customerId;

      @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
      private UUID roomId;

      @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
      private UUID roomCategoryId;

//      @NotNull(message = INVALID_NAME)
////      @NotEmpty(message = INVALID_NAME)
//      private Double price;

      @NotNull(message = INVALID_NAME)
//      @NotEmpty(message = INVALID_NAME)
      private Date checkInDate;

      @NotNull(message = INVALID_NAME)
//      @NotEmpty(message = INVALID_NAME)
      private Date checkOutDate;

      @NotNull(message = INVALID_NAME)
//      @NotEmpty(message = INVALID_NAME)
      private Integer night;

      @NotNull(message = INVALID_NAME)
//      @NotEmpty(message = INVALID_NAME)
      private Integer bookedNo;

}
