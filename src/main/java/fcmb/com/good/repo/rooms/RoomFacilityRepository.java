package fcmb.com.good.repo.rooms;

import fcmb.com.good.model.entity.rooms.ProductFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomFacilityRepository extends JpaRepository<ProductFacility, Long> {

    Optional<ProductFacility> findByName (String name);

    @Query("select st from ProductFacility st where st.uuid=:recordId")
    Optional<ProductFacility> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from ProductFacility st where st.uuid=:recordId")
    Optional<ProductFacility> deleteByUuid(@Param("recordId")UUID uuid);

    @Query("select st from ProductFacility st where st.room.uuid=:roomUuid")
    List<ProductFacility> findRoomFacilityByRoomNumberAndCustomer(@Param("roomUuid")UUID roomUuid);

    @Query("SELECT st FROM ProductFacility st WHERE " +
            "st.name LIKE CONCAT('%',:query, '%')" )
    List<ProductFacility> searchRoomFacilityByName(String query);

}

