package fcmb.com.good.model.dto.response.userResponse;


import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

@Data
public class EmployeeShiftResponse extends BaseDto {

     private Long employee_id;
     private String designation;
     private String shift;
     private String period;

}
