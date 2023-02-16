package fcmb.com.good.model.dto.request.productsRequest;

import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import static fcmb.com.good.utills.MessageUtil.INVALID_NAME;

@Data
public class ProductPurchaseRequest  {

     @NotNull(message = INVALID_NAME)
     @NotEmpty(message = INVALID_NAME)
     private String name;

     @NotNull(message = INVALID_NAME)
     @NotEmpty(message = INVALID_NAME)
     private String category;

     @NotNull(message = INVALID_NAME)
     @NotEmpty(message = INVALID_NAME)
     private String description;

     @NotNull(message = INVALID_NAME)
     @NotEmpty(message = INVALID_NAME)
     private String companyName;

     @NotNull(message = INVALID_NAME)
     @NotEmpty(message = INVALID_NAME)
     private String quantity;

     @NotNull(message = INVALID_NAME)
//     @NotEmpty(message = INVALID_NAME)
     private Double price;

     @NotNull(message = INVALID_NAME)
//   @NotEmpty(message = INVALID_NAME)
     private UUID productCategory;

     @NotNull(message = INVALID_NAME)
//     @NotEmpty(message = INVALID_NAME)
     private UUID createdBy;

     @NotNull(message = INVALID_NAME)
//     @NotEmpty(message = INVALID_NAME)
     private UUID product;

     @NotNull(message = INVALID_NAME)
//     @NotEmpty(message = INVALID_NAME)
     private Date productPurchaseDate;

     @NotNull(message = INVALID_NAME)
//     @NotEmpty(message = INVALID_NAME)
     private LocalDateTime dateCreated;

     @NotNull(message = INVALID_NAME)
//     @NotEmpty(message = INVALID_NAME)
     private LocalDateTime lastModified;


}
