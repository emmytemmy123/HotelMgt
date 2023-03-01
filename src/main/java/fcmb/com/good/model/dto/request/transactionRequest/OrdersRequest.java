package fcmb.com.good.model.dto.request.transactionRequest;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

import static fcmb.com.good.utills.MessageUtil.INVALID_NAME;

@Data
public class OrdersRequest {

    @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private Integer orderNo;

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String orderBy;

    @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private Double amount;

    @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private Double amountDue;

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String orderStatus;

//    @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private Timestamp startTime;

//    @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private Timestamp endTime;

    @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private UUID orderItemsId;

    @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private UUID customerId;

    @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private UUID productId;



}
