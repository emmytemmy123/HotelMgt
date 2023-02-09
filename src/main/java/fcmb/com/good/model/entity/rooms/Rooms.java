package fcmb.com.good.model.entity.rooms;


import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.entity.others.Document;
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
    private String room_type;
    private String room_no;
    private String room_description;
    private String price;
    private String room_status;
    private String [] items;
    private Boolean isAvailable;
    private String available_rooms;
    private String state;

    @OneToMany(mappedBy = "roomId")
    private List<RoomFacility> roomFacilityId;
    private String current_customer;

    @OneToMany(mappedBy = "rooms")
    private List<Document> documentList;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "room_type_id")
    private RoomType roomtype;


    public Rooms() {
    }


}
