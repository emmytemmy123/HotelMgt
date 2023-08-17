package fcmb.com.good.model.dto.request.activityLog;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static fcmb.com.good.utills.MessageUtil.INVALID_DESCRIPTION;
import static fcmb.com.good.utills.MessageUtil.INVALID_NAME;

@Data
public class ActivityLogCategory {

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String name;

//    @NotNull(message = INVALID_DESCRIPTION)
//    @NotEmpty(message = INVALID_DESCRIPTION)
    private String description;




}
