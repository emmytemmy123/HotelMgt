package fcmb.com.good.repo.rooms;

import fcmb.com.good.model.entity.rooms.RoomFacility;
import fcmb.com.good.model.entity.rooms.Rooms;
import fcmb.com.good.model.entity.services.SubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomFacilityRepository extends JpaRepository<RoomFacility, Long> {

    Optional<RoomFacility> findByName (String name);

    @Query("select st from RoomFacility st where st.uuid=:recordId")
    Optional<RoomFacility> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from RoomFacility st where st.uuid=:recordId")
    Optional<RoomFacility> deleteByUuid(@Param("recordId")UUID uuid);

    @Query("select st from RoomFacility st where st.room.uuid=:roomUuid")
    List<RoomFacility> findRoomFacilityByRoomNumberAndCustomer(@Param("roomUuid")UUID roomUuid);

    @Query("SELECT st FROM RoomFacility st WHERE " +
            "st.name LIKE CONCAT('%',:query, '%')" )
    List<RoomFacility> searchRoomFacilityByName(String query);

}

