package fcmb.com.good.model.dto.request.userRequest;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static fcmb.com.good.utills.MessageUtil.INVALID_DEPARTMENT;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserCategoryRequest {

    private String name;
    private String description;


}
