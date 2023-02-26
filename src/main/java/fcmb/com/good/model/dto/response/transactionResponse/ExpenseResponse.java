package fcmb.com.good.model.dto.response.transactionResponse;


import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

import java.util.Date;

@Data
public class ExpenseResponse extends BaseDto {

     private String Category;
     private String name;
     private String description;
     private Double price;
     private Integer quantity;
     private Double totalAmount;
     private Date dateOfExpense;
     private String expensedBy;

}
