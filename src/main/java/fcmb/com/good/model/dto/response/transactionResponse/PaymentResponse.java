package fcmb.com.good.model.dto.response.transactionResponse;


import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

import java.util.Date;

@Data
public class PaymentResponse extends BaseDto {

     private Double amount;
     private String description;
     private String paidBy;
     private String paymentMode;
     private String paymentStatus;
     private String postedBy;
     private String tranReference;

     private PaymentHelper order;

}
