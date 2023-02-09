package fcmb.com.good.repo.rooms;

import fcmb.com.good.model.entity.rooms.RoomFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomFacilityRepo extends JpaRepository<RoomFacility,Long> {
    @Query("select st from RoomFacility st where st.roomId=:roomNumber ")
    List<RoomFacility> findRoomFacilitiesByRoom(Long roomNumber);


}
