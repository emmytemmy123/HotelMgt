package fcmb.com.good.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BaseDto {

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime dateCreated;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime lastModified;

    private UUID uuid;

}
