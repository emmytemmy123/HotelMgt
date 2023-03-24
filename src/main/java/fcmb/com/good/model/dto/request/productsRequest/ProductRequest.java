package fcmb.com.good.model.dto.request.productsRequest;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import static fcmb.com.good.utills.MessageUtil.INVALID_NAME;

@Data
public class ProductRequest  {

     @NotNull(message = INVALID_NAME)
     @NotEmpty(message = INVALID_NAME)
     private String name;

     @NotNull(message = INVALID_NAME)
     @NotEmpty(message = INVALID_NAME)
     private String category;

//   @NotNull(message = INVALID_NAME)
//   @NotEmpty(message = INVALID_NAME)
     private String brand;

//   @NotNull(message = INVALID_NAME)
//   @NotEmpty(message = INVALID_NAME)
     private Date expDate;

     @NotNull(message = INVALID_NAME)
     @NotEmpty(message = INVALID_NAME)
     private String description;

     @NotNull(message = INVALID_NAME)
     @Min(value = 1, message = INVALID_NAME)
     private Integer durations;

     @NotNull(message = INVALID_NAME)
     @Min(value=50, message = INVALID_NAME)
     private Double salesPrice;

     @NotNull(message = INVALID_NAME)
     @Min(value = 1, message = INVALID_NAME)
     private Integer quantity;

     @NotNull(message = INVALID_NAME)
     private UUID categoryId;

     @NotNull(message = INVALID_NAME)
     private UUID createdById;

     @NotNull(message = INVALID_NAME)
     @Min(value=50, message = INVALID_NAME)
     private Double purchasedPrice;


}
