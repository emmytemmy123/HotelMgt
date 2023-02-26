package fcmb.com.good.model.dto.response.productsResponse;


import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ProductPurchaseResponse extends BaseDto {

     private String name;
     private String category;
     private String description;
     private String companyName;
     private String brand;
     private String quantity;
     private Double price;
     private Date productPurchaseDate;


}
