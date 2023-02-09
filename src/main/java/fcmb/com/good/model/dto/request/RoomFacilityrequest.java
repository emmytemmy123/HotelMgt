package fcmb.com.good.model.dto.request;

import fcmb.com.good.model.entity.rooms.Rooms;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.File;

import static fcmb.com.good.utills.MessageUtil.INVALID_NAME;

@Data
public class RoomFacilityrequest {

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String roomName;

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String fileName;

    @NotNull(message = INVALID_NAME)
    @NotEmpty(message = INVALID_NAME)
    private String description;
}
