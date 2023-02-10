package fcmb.com.good.model.dto.response.productsResponse;


import fcmb.com.good.model.dto.BaseDto;
import fcmb.com.good.model.entity.products.ProductCategory;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static fcmb.com.good.utills.MessageUtil.INVALID_NAME;

@Data
public class ProductResponse extends BaseDto {


     private String name;

     private String description;

     private Integer quantity;

     private Double price;

     private UUID category;

     private String code;

     private String location;

     private String status;

     private UUID createdBy;

     private Double purchasedPrice;

     private Double profit;

     private String productsCategory;



}
