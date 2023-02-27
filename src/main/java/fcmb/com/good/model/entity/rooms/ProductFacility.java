package fcmb.com.good.model.entity.rooms;


import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@EntityListeners(BaseListener.class)
@Entity
@Table(name = "roomFacility")
public class ProductFacility extends BaseEntity {

    private String name;
    private String fileName;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "existingRoomId",insertable = true, updatable = true)
    private Rooms room;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "createdById", insertable = true, updatable = true)
    private AppUser createdBy;




    public ProductFacility(){}

}
