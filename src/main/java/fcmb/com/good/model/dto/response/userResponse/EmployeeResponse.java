package fcmb.com.good.model.dto.response.userResponse;


import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

@Data
public class EmployeeResponse extends BaseDto {

     private String name;
     private String email;
     private String gender;
     private String photo;
     private String country;
     private String city;
     private String address;
     private String phone;
     private String designation;
     private String username;
     private String createdById;

}
