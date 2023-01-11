package fcmb.com.good.model.dto.response.userResponse;

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
public class forgotUserPasswordResponse {
    @NotNull(message = INVALID_PASSWORD)
    @NotEmpty(message = INVALID_PASSWORD)
    String email;
}