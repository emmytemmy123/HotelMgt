package fcmb.com.good.repo.transaction;


import fcmb.com.good.model.entity.transaction.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    @Query("select st from Orders st where st.uuid=:recordId")
    Optional<Orders> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from Orders st where st.uuid=:recordId")
    Optional<Orders> deleteByUuid(@Param("recordId")UUID uuid);


}
