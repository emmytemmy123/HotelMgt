package fcmb.com.good.model.dto.response.userResponse;

import lombok.Data;

import java.util.UUID;

@Data
public class CustomerHelper {

    private String nin;
    private UUID uuid;
    private String name;


}
