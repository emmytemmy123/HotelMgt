package fcmb.com.good.model.dto.response.servicesResponse;

import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

@Data
public class SubServiceResponse extends BaseDto {

    private String serviceName;
    private Double unitCost;


}
