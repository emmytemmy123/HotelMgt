package fcmb.com.good.model.entity.rooms;

import fcmb.com.good.model.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.File;
@AllArgsConstructor
@Entity
@Table(name ="room_facility")
@Data
public class RoomFacility extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id", insertable = false, updatable = false)
  private Rooms  roomId; // (Foreign key to Room)

    private String roomName;

    private File fileName;

    private String description;


}
