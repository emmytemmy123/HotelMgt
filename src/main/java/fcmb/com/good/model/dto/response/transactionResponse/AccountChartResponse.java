package fcmb.com.good.model.dto.response.transactionResponse;


import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

@Data
public class AccountChartResponse extends BaseDto {

     private Long category_id;
     private String code;
     private String account_name;
     private Double balance;

}
