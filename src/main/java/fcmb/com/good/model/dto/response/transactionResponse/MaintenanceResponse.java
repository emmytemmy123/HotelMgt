package fcmb.com.good.model.dto.response.transactionResponse;


import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

@Data
public class MaintenanceResponse extends BaseDto {

     private String category;
     private String name;
     private String description;
     private String comment;
     private String quantity;
     private Double cost;
     private Double amount;
     private String status;
     private String requestedBy;
     private String maintainedBy;

}
