package fcmb.com.good.repo.transaction;

import fcmb.com.good.model.entity.transaction.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query(value = "SELECT * FROM payment WHERE date_created BETWEEN :start_date AND :end_date", nativeQuery = true)
    List<Payment> findByDateCreatedBetween ( String start_date, String end_date);

    @Query("select st from Payment st where st.uuid=:recordId")
    Optional<Payment> findByUuid(@Param("recordId") UUID uuid);

    @Query("select st from Payment st where st.order.uuid=:recordId")
    List<Payment> findByOrderId(@Param("recordId") UUID uuid);

    @Query("delete from Payment st where st.uuid=:recordId")
    Optional<Payment> deleteByUuid(@Param("recordId")UUID uuid);

//    Optional<Payment> findByName(String name);

    @Query("SELECT st FROM Payment st WHERE st.dateCreated LIKE CONCAT('%',:query,'%') ")
    List<Payment> findByDateCreated(String query);

    @Query("select st from Payment st where st.order.customer.createdBy.uuid=:recordId")
    List<Payment> findPaymentBySalesPerson(@Param("recordId") UUID uuid);

    @Query("select st from Payment st where st.order.customer.uuid=:recordId")
    List<Payment> findPaymentByCustomer(@Param("recordId") UUID uuid);

}
