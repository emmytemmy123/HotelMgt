package fcmb.com.good.model.dto.response.productsResponse;


import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Data
public class ProductResponse extends BaseDto {

     private String category;
     private String brand;
     private String name;
     private String description;
     private Integer quantity;
     private Double salesPrice;
     private Integer durations;
     private Double purchasePrice;
     private Date expDate;
     private String postedBy;

     private List<ProductHelperResponse> productFacilityList;


}
