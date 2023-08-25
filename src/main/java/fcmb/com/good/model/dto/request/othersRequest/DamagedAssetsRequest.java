package fcmb.com.good.model.dto.request.othersRequest;

import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.UUID;

import static fcmb.com.good.utills.MessageUtil.INVALID_NAME;

@Data
public class DamagedAssetsRequest  {

     @NotNull(message = INVALID_NAME)
//     @NotEmpty(message = INVALID_NAME)
     private String name;

     @NotNull(message = INVALID_NAME)
//     @NotEmpty(message = INVALID_NAME)
     private String employeeName;

     @NotNull(message = INVALID_NAME)
//     @NotEmpty(message = INVALID_NAME)
     private Integer quantity;

     @NotNull(message = INVALID_NAME)
//     @NotEmpty(message = INVALID_NAME)
     private Double price;

     @NotNull(message = INVALID_NAME)
//     @NotEmpty(message = INVALID_NAME)
     private String comment;

     @NotNull(message = INVALID_NAME)
//     @NotEmpty(message = INVALID_NAME)
     private String location;

     @NotNull(message = INVALID_NAME)
//     @NotEmpty(message = INVALID_NAME)
     private String category;

     @NotNull(message = INVALID_NAME)
//     @NotEmpty(message = INVALID_NAME)
     private String approveStatus;

     @NotNull(message = INVALID_NAME)
//     @NotEmpty(message = INVALID_NAME)
     private String repairedStatus;

     @NotNull(message = INVALID_NAME)
//     @NotEmpty(message = INVALID_NAME)
     private UUID createdById;



}
