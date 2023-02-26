package fcmb.com.good.model.dto.response.servicesResponse;


import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

@Data
public class ServiceRequestResponse extends BaseDto {

     private String serviceName;
     private String serviceBy;
     private  Integer serviceRequestNo;
     private String currentCustomer;
     private String serviceCategory;
     private Double price;
     private String paymentStatus;

}
