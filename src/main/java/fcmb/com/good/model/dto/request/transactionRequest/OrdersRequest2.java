package fcmb.com.good.model.dto.request.transactionRequest;


import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;


@Data
public class OrdersRequest2 {


    //    @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private Integer numberOfDays;

    private List<OrderItemRequest> items;


}
