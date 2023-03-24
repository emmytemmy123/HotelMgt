package fcmb.com.good.model.dto.request.servicesRequest;

import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static fcmb.com.good.utills.MessageUtil.INVALID_NAME;

@Data
public class ServicesRequest  {

        @NotNull(message = INVALID_NAME)
        @NotEmpty(message = INVALID_NAME)
        private String serviceType;

        @NotNull(message = INVALID_NAME)
        @NotEmpty(message = INVALID_NAME)
        private String description;

        @NotNull(message = INVALID_NAME)
        @NotEmpty(message = INVALID_NAME)
        private String serviceCategory;

}
