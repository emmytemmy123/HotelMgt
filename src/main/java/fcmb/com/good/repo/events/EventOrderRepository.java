package fcmb.com.good.repo.events;


import fcmb.com.good.model.entity.Events.EventOrder;
import fcmb.com.good.model.entity.Events.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventOrderRepository extends JpaRepository<EventOrder, Long> {

    @Query("select st from EventOrder st where st.uuid=:recordId")
    Optional<EventOrder> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from EventOrder st where st.uuid=:recordId")
    Optional<EventOrder> deleteByUuid(@Param("recordId")UUID uuid);

//    @Query("select st from EventOrder st where st.name=:recordId")
//    Optional<EventOrder> findAssetByName(@Param("recordId") String name);

}
