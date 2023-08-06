package fcmb.com.good.repo.transaction;


import fcmb.com.good.model.entity.transaction.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {

    @Query("select st from OrderItems st where st.uuid=:recordId")
    Optional<OrderItems> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from OrderItems st where st.uuid=:recordId")
    Optional<OrderItems> deleteByUuid(@Param("recordId")UUID uuid);

    Optional<OrderItems> findByItemName(String itemName);



}
