package fcmb.com.good.model.dto.response.transactionResponse;


import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

import java.util.Date;

@Data
public class BookingResponse extends BaseDto {

     private String category;
     private Double price;
     private Integer roomNo;
     private Integer bookedNo;
     private String bookedBy;
     private Date checkInDate;
     private Date checkOutDate;
     private String night;
     private Double totalAmount;


}
