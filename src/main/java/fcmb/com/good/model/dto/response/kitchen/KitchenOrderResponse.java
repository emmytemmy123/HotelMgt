package fcmb.com.good.model.dto.response.kitchen;


import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

@Data
public class KitchenOrderResponse extends BaseDto {

    private String foodName;
    private String category;
    private Integer quantity;
    private Double price;
    private Integer requestNo;
    private String accountNo;
    private String salesPerson;
    private String status;
    private String currentCustomer;

}
