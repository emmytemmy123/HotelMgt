package fcmb.com.good.model.dto.request.userRequest;


import fcmb.com.good.model.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static fcmb.com.good.utills.MessageUtil.INVALID_ADDRESS;
import static fcmb.com.good.utills.MessageUtil.INVALID_DEPARTMENT;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoleRequest  {

    @NotNull(message = INVALID_DEPARTMENT)
    @NotEmpty(message = INVALID_DEPARTMENT)
    private String department;

}
