package fcmb.com.good.model.dto.request.kitchen;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.UUID;

import static fcmb.com.good.utills.MessageUtil.INVALID_NAME;


@Data
public class KitchenOrderRequest {

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String foodName;

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String category;

    @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private Integer quantity;

//    @NotNull(message = INVALID_NAME)
////    @NotEmpty(message = INVALID_NAME)
//    private Double price;


    @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private Integer requestNo;

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String accountNo;


    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String status;

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String description;

    @NotNull(message = INVALID_NAME)
    private UUID createdById;

    @NotNull(message = INVALID_NAME)
    private UUID currentKitchenCategoryId;

    @NotNull(message = INVALID_NAME)
    private UUID CurrentKitchenById;

    @NotNull(message = INVALID_NAME)
    private UUID CurrentCustomerById;

    @NotNull(message = INVALID_NAME)
    private UUID CurrentRoomById;

    @NotNull(message = INVALID_NAME)
    private UUID CurrentEmployeeById;







}



