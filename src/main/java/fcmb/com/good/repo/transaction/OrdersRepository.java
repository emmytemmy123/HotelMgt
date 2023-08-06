package fcmb.com.good.repo.transaction;


import fcmb.com.good.model.entity.transaction.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    @Query("select st from Orders st where st.uuid=:recordId")
    Optional<Orders> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from Orders st where st.uuid=:recordId")
    Optional<Orders> deleteByUuid(@Param("recordId")UUID uuid);

    @Query("select st from Orders st where st.customer.uuid=:customerUuid")
    List<Orders> findOrdersByCustomer(@Param("customerUuid") UUID customerUuid);

//    @Query("select st from Orders st where st.dateCreated=:dateCreated")
    @Query("SELECT p FROM Orders p WHERE p.dateCreated LIKE CONCAT('%',:query, '%')")
    List<Orders> searchOrderByDateCreated(String query);

    @Query( value = "select * from orders where month(date_created) =:month and year(date_created) =:year order by id Desc limit 1", nativeQuery = true)
    List<Orders> findOrderForCurrentDate(@Param("month")Integer month, @Param("year") Integer year);



}
