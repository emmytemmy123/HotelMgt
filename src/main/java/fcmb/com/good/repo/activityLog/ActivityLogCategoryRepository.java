package fcmb.com.good.repo.activityLog;


import fcmb.com.good.model.entity.activityLog.ActivityLogCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface ActivityLogCategoryRepository extends JpaRepository<ActivityLogCategory, Long> {


    @Query("select st from ActivityLogCategory st where st.uuid=:recordId")
    Optional<ActivityLogCategory> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from ActivityLogCategory st where st.uuid=:recordId")
    Optional<ActivityLogCategory> deleteByUuid(@Param("recordId")UUID uuid);


}
