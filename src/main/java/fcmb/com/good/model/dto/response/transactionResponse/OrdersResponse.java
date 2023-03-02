package fcmb.com.good.model.dto.response.transactionResponse;

import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class OrdersResponse extends BaseDto {

    private Integer orderNo;
    private String orderBy;
    private Double amount;
    private Double amountDue;
    private String orderStatus;
    private Timestamp startTime;
    private Timestamp endTime;

}
