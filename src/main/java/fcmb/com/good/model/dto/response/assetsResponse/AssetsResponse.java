package fcmb.com.good.model.dto.response.assetsResponse;

import fcmb.com.good.model.dto.BaseDto;
import lombok.Data;

@Data
public class AssetsResponse extends BaseDto {

    private String name;
    private String description;
    private String code;
    private Integer quantity;
    private String status;
    private String photo;

    private AssetsHelper assetsCategory;

}
