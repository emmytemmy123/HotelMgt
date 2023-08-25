package fcmb.com.good.model.dto.response.eventsResponse;

import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

@Data
public class EventsCategoryResponse extends BaseDto {

    private String name;
    private String description;
    private Long accountNo;

}
