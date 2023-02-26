package fcmb.com.good.model.dto.response.userResponse;

import fcmb.com.good.model.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static fcmb.com.good.utills.MessageUtil.INVALID_PASSWORD;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class changeUserPasswordResponse extends BaseDto {


    String oldPassword;

    String newPassword;

}
