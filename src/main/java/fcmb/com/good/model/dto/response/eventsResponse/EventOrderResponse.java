package fcmb.com.good.model.dto.response.eventsResponse;


import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventOrderResponse extends BaseDto {

    private String eventName;
    private String orderBy;
    private String phone;
    private String description;
    private Integer capacity;
    private String status;
    private String photo;
    private String category;
    private Double price;
    private String paymentStatus;
    private LocalDateTime eventDay;

}
