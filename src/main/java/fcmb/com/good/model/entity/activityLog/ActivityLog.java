package fcmb.com.good.model.entity.activityLog;


import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@EntityListeners(BaseListener.class)
@Entity
@Table(name = "activityLog")
public class ActivityLog extends BaseEntity {

    private String category;
    private String name;
    private String description;
    private String performedBy;
    private LocalDateTime performedDate;
    private Double amountPaid;




    public ActivityLog(){}


}
