package fcmb.com.good.model.dto.response.servicesResponse;


import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

@Data
public class SubServiceRequestResponse extends BaseDto {

     private String serviceName;
     private String roomNo;
     private Integer noOfOccupant;
     private Double price;
     private String customerName;
     private String status;
     private String orderNo;

}
