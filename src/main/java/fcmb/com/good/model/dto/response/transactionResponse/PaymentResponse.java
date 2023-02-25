package fcmb.com.good.model.dto.response.transactionResponse;


import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

import java.util.Date;

@Data
public class PaymentResponse extends BaseDto {

     private String category;
     private Double amount;
     private String name;
     private Double price;
     private Integer quantity;
     private String paymentStatus;
     private String paymentDetails;
     private String paidBy;
}
