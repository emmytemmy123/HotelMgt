package fcmb.com.good.model.dto.response.othersResponse;


import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

@Data
public class HotelResponse extends BaseDto {

     private String name;
     private String email;
     private String country;
     private String state;
     private String city;
     private String address;
     private String phone;

}
