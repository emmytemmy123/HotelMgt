package fcmb.com.good.model.dto.response.transactionResponse;

import fcmb.com.good.model.dto.BaseDto;
import fcmb.com.good.model.dto.response.servicesResponse.SubServiceHelper;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class OrdersResponse extends BaseDto {

    private String orderNo;
    private String orderBy;
    private Double amount;
    private Double amountDue;
    private String orderStatus;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private List<OrderHelperResponse> orderItemsList;
    private List<SubServiceHelper> subServiceRequestList;


}

