package fcmb.com.good.repo.transaction;

import fcmb.com.good.model.entity.transaction.ExpenseCategory;
import fcmb.com.good.model.entity.transaction.MaintenanceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MaintenanceCategoryRepository extends JpaRepository<MaintenanceCategory, Long> {

    @Query("select st from MaintenanceCategory st where st.uuid=:recordId")
    Optional<MaintenanceCategory> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from MaintenanceCategory st where st.uuid=:recordId")
    Optional<MaintenanceCategory> deleteByUuid(@Param("recordId")UUID uuid);

    @Query("select st from MaintenanceCategory st where st.name=:category")
    Optional<MaintenanceCategory> findByName(@Param("category") String name);


}
