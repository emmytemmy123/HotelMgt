package fcmb.com.good.model.entity.others;


import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.entity.Events.Events;
import fcmb.com.good.model.entity.user.Users;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;


@Data
@AllArgsConstructor
@EntityListeners(BaseListener.class)
@Entity
@Table(name = "damagedAsset")
public class DamagedAssets extends BaseEntity {

    private String name;
    private String employeeName;
    private Integer quantity;
    private Double price;
    private String status;
    private String comment;
    private String location;
    private String category;
    private String approvedStatus;
    private String repairedStatus;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "eventsId", insertable = true, updatable = true)
    private Events events;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "createdById", updatable = true)
    private Users createdBy;

    public DamagedAssets(){

    }


}
