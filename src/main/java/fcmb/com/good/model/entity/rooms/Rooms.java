package fcmb.com.good.model.entity.rooms;


import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.entity.others.Document;
import fcmb.com.good.model.entity.services.SubService;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.model.entity.user.Customer;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@EntityListeners(BaseListener.class)
@Entity
@Table(name = "rooms")
public class Rooms extends BaseEntity {

    private String serviceType;
    private Integer serviceNumber;
    private String description;
    private Double price;
    private String status;
    private Integer rate;
    private Integer maxNoOccupant;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "currentOccupantId", insertable = true, updatable = true)
    private Customer currentOccupant;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "createdBy_id", updatable = true)
    private AppUser createdBy;

    @OneToMany(mappedBy = "rooms")
    private List<Document> documentList;

    @OneToMany(mappedBy = "rooms")
    private List<SubService> subServiceList;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "room_type_id", updatable = true)
    private RoomCategory roomCategory;


    public Rooms() {
    }


}
