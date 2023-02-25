package fcmb.com.good.repo.transaction;

import fcmb.com.good.model.entity.transaction.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

    @Query("select st from Maintenance st where st.uuid=:recordId")
    Optional<Maintenance> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from Maintenance st where st.uuid=:recordId")
    Optional<Maintenance> deleteByUuid(@Param("recordId")UUID uuid);

    Optional<Maintenance> findByName(String name);


}
