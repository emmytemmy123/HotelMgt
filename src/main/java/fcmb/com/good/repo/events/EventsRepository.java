package fcmb.com.good.repo.events;

import fcmb.com.good.model.entity.Events.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventsRepository extends JpaRepository<Events, Long> {

    @Query("select st from Events st where st.uuid=:recordId")
    Optional<Events> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from Events st where st.uuid=:recordId")
    Optional<Events> deleteByUuid(@Param("recordId")UUID uuid);

    @Query("select st from Events st where st.name=:recordId")
    Optional<Events> findAssetByName(@Param("recordId") String name);


}
