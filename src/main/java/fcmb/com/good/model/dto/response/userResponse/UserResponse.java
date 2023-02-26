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
public class UserResponse extends BaseDto {

     private String name;
     private String email;
     private String phone;
     private String address;
     private String city;
     private String country;
     private String gender;
     private String photo;
     private String username;


}
