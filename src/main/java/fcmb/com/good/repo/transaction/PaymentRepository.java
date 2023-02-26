package fcmb.com.good.repo.transaction;

import fcmb.com.good.model.entity.transaction.Payment;
import fcmb.com.good.model.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("select st from Payment st where st.uuid=:recordId")
    Optional<Payment> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from Payment st where st.uuid=:recordId")
    Optional<Payment> deleteByUuid(@Param("recordId")UUID uuid);

//    Optional<Payment> findByName(String name);

}
