package fcmb.com.good.model.dto.request.productsRequest;

import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.UUID;

import static fcmb.com.good.utills.MessageUtil.INVALID_NAME;

@Data
public class ProductOrderRequest  {


    @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private Double amount;

    @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private Double tax;

    @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private Integer orderNo;

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String accountNo;

    @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private Double profit;

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String salesPerson;

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String orderState;

    @NotNull(message = INVALID_NAME)
    private UUID employeeId;

    @NotNull(message = INVALID_NAME)
    private UUID customerId;

    @NotNull(message = INVALID_NAME)
    private UUID productId;

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String productName;

    @NotNull(message = INVALID_NAME)
    private Integer quantity;

    @NotNull(message = INVALID_NAME)
    private Double price;

}
