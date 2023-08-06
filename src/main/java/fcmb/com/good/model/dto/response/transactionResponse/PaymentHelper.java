package fcmb.com.good.model.dto.response.transactionResponse;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentHelper {

    private String orderNo;
    private String orderBy;
    private Double amount;
    private Double amountDue;
    private String orderStatus;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
