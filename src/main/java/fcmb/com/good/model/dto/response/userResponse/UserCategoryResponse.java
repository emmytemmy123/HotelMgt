package fcmb.com.good.model.dto.response.userResponse;


import fcmb.com.good.model.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserCategoryResponse extends BaseDto {

    private String name;
    private String description;


}
