package fcmb.com.good.model.dto.response.productsResponse;


import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductOrderResponse extends BaseDto {

     private String productName;
     private Double amount;
     private Double tax;
     private Long orderNo;
     private Long accountNo;
     private Double profit;
     private String salesPerson;
     private String orderState;
     private String currentCustomer;
     private Integer quantity;
     private Double price;


}
