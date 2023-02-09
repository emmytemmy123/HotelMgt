package fcmb.com.good.model.entity.rooms;

import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
@AllArgsConstructor
@Entity
//@EntityListeners(value = BaseListener.class)
@Table(name ="room_facility")
@Data
public class RoomFacility extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id", insertable = true)
  private Rooms  roomId; // (Foreign key to Room)

    private String roomName;

    private String fileName;

    private String description;


    public RoomFacility() {

    }
}
