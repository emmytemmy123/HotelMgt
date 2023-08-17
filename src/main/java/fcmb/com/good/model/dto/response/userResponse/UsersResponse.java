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
public class UsersResponse extends BaseDto {

     private String name;
     private String email;
     private String phone;
     private String address;
     private String photo;
     private String gender;
     private String country;
     private String city;
     private String username;
     private String nin;


}
