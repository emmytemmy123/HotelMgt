package fcmb.com.good.model.dto.response.transactionResponse;


import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

@Data
public class AccountCategoryResponse extends BaseDto {

     private String code;
     private String currency;
     private String accountType;
     private String postedBy;

}
