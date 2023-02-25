package fcmb.com.good.model.dto.response.kitchen;

import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

@Data
public class KitchenResponse extends BaseDto {

    private String foodName;
    private String description;
    private Integer quantity;
    private Double price;
    private String status;
    private String category;
    private String photo;


}
