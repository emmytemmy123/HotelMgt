package fcmb.com.good.model.dto.request.transactionRequest;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

import static fcmb.com.good.utills.MessageUtil.INVALID_NAME;

@Data
public class OrdersUpdateRequest {

//    @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private String productStatus;

//    @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private String roomStatus;



    private List<OrderItemRequest> items;


}
