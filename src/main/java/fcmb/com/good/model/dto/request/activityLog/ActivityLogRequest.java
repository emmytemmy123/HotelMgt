package fcmb.com.good.model.dto.request.activityLog;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static fcmb.com.good.utills.MessageUtil.INVALID_DATE_FILTER;
import static fcmb.com.good.utills.MessageUtil.INVALID_NAME;

@Data
public class ActivityLogRequest {

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String category;

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String name;

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String description;

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String performedBy;

    @NotNull(message = INVALID_DATE_FILTER)
    @NotEmpty(message = INVALID_DATE_FILTER)
    private LocalDateTime performedDate;

    @NotNull(message = INVALID_DATE_FILTER)
    @NotEmpty(message = INVALID_DATE_FILTER)
    private LocalDateTime loginDate;



}
