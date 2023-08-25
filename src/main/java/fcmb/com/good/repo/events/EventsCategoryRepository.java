package fcmb.com.good.repo.events;

import fcmb.com.good.model.entity.Events.EventsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventsCategoryRepository extends JpaRepository<EventsCategory, Long> {

    @Query("select st from EventsCategory st where st.uuid=:recordId")
    Optional<EventsCategory> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from EventsCategory st where st.uuid=:recordId")
    Optional<EventsCategory> deleteByUuid(@Param("recordId")UUID uuid);

    @Query("select st from EventsCategory st where st.name=:recordId")
    Optional<EventsCategory> findEventCategoryByName(@Param("recordId") String name);

}
