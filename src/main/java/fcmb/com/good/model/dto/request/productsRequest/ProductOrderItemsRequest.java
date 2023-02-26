package fcmb.com.good.model.dto.request.productsRequest;

import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static fcmb.com.good.utills.MessageUtil.*;

@Data
public class ProductOrderItemsRequest {

     @NotNull(message = PRODUCT_ID)
     @NotEmpty(message = PRODUCT_ID)
     private Long product_id;

     @NotNull(message = PRODUCT_ORDER_ID)
     @NotEmpty(message = PRODUCT_ORDER_ID)
     private Long product_order_id;

     @NotNull(message = PRODUCT_NAME)
     @NotEmpty(message = PRODUCT_NAME)
     private String product_name;

     @NotNull(message = QUANTITY)
     @NotEmpty(message = QUANTITY)
     private String quantity;

     @NotNull(message = PRICE)
     @NotEmpty(message = PRICE)
     private Double price;

}
