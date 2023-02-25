package fcmb.com.good.repo.transaction;


import fcmb.com.good.model.entity.transaction.MaintenanceCategory;
import fcmb.com.good.model.entity.transaction.Payment;
import fcmb.com.good.model.entity.transaction.PaymentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentCategoryRepository extends JpaRepository<PaymentCategory, Long> {

    @Query("select st from PaymentCategory st where st.uuid=:recordId")
    Optional<PaymentCategory> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from PaymentCategory st where st.uuid=:recordId")
    Optional<PaymentCategory> deleteByUuid(@Param("recordId")UUID uuid);

    @Query("select st from PaymentCategory st where st.name=:category")
    Optional<PaymentCategory> findByName(@Param("category") String name);

}
