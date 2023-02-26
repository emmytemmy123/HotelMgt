package fcmb.com.good.model.dto.response.servicesResponse;


import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

@Data
public class ServiceResponse extends BaseDto {

     private String service_type;
     private String description;
     private String service_category;

}
