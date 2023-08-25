package fcmb.com.good.repo.transaction;

import fcmb.com.good.model.entity.transaction.MaintenanceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, Long> {

    @Query("select st from MaintenanceRequest st where st.uuid=:recordId")
    Optional<MaintenanceRequest> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from MaintenanceRequest st where st.uuid=:recordId")
    Optional<MaintenanceRequest> deleteByUuid(@Param("recordId")UUID uuid);

    Optional<MaintenanceRequest> findByName(String name);


}
