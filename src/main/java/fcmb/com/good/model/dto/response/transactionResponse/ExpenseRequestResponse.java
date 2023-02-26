package fcmb.com.good.model.dto.response.transactionResponse;


import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

@Data
public class ExpenseRequestResponse extends BaseDto {

     private Long expense_id;
     private Long employee_id;
     private String quantity;
     private Double amount;
     private Long account_no;
     private String status;

}
