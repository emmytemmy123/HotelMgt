package fcmb.com.good.model.dto.response.transactionResponse;


import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

@Data
public class ExpenseCategoryResponse extends BaseDto {

    private String name;
    private String description;
    private String postedBy;

}
