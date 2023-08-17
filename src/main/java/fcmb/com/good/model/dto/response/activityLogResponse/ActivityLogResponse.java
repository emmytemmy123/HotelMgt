package fcmb.com.good.model.dto.response.activityLogResponse;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityLogResponse {

    private String category;
    private String name;
    private String description;
    private String performedBy;
    private LocalDateTime performedDate;
    private Double amountPaid;




}
