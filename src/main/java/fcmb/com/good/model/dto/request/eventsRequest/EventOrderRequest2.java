package fcmb.com.good.model.dto.request.eventsRequest;


import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

import static fcmb.com.good.utills.MessageUtil.INVALID_NAME;

@Data
public class EventOrderRequest2 {

    @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private String orderBy;

        @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private String description;

        @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private String photo;

        @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private String phone;

        @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private String status;

    @NotNull(message = INVALID_NAME)
//    @NotEmpty(message = INVALID_NAME)
    private Date eventDay;




}
