package fcmb.com.good.model.dto.response.transactionResponse;


import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

import java.util.Date;

@Data
public class BookingReminderResponse extends BaseDto {

     private Long customer_id;
     private Long booking_id;
     private String reminder_details;
     private String status;

}
