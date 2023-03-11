package fcmb.com.good.repo.transaction;


import fcmb.com.good.model.entity.transaction.Orders;
import fcmb.com.good.model.entity.transaction.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    @Query("select st from Orders st where st.uuid=:recordId")
    Optional<Orders> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from Orders st where st.uuid=:recordId")
    Optional<Orders> deleteByUuid(@Param("recordId")UUID uuid);

    @Query("select st from Orders st where st.customer.uuid=:recordId")
    List<Orders> findByCustomerUuid(@Param("recordId") UUID uuid);

    @Query("SELECT st FROM Orders st WHERE " +
            "st.dateCreated LIKE CONCAT('%',:query, '%')" )
    List<Orders> findOrderByDateCreated(String query);

//    @Query("select st from Orders st where st.dateCreated = LocalDateTime().now order by id desc limit 1")
    Optional<Orders> findByDateCreated(@Param("LocalDateTime().now") LocalDateTime localDateTime);

    @Query(value="SELECT * FROM orders WHERE CONVERT(date, date_created) = CONVERT(date, GETDATE()) order by id desc limit 1"nativeQuery = true)
    Optional<Orders> findOrderForCurrentDate();
//SELECT * FROM table_name WHERE CONVERT(date, date_column) = CONVERT(date, GETDATE());



}
