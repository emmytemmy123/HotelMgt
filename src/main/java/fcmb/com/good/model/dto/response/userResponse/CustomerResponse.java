package fcmb.com.good.model.dto.response.userResponse;

import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

import java.util.UUID;

@Data
public class CustomerResponse extends BaseDto {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String nin;
    private String city;
    private String country;
    private String gender;
    private String photo;
    private String username;
    private UUID createdById;
}
