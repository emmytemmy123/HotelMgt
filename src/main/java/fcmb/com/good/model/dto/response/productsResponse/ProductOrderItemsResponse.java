package fcmb.com.good.model.dto.response.productsResponse;


import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

@Data
public class ProductOrderItemsResponse extends BaseDto {

     private Long product_id;
     private Long product_order_id;
     private String product_name;
     private String quantity;
     private Double price;

}
