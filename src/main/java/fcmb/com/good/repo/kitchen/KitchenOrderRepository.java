package fcmb.com.good.repo.kitchen;

import fcmb.com.good.model.entity.kitchen.KitchenCategory;
import fcmb.com.good.model.entity.kitchen.KitchenOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface KitchenOrderRepository extends JpaRepository<KitchenOrder, Long> {

    @Query("select st from KitchenOrder st where st.uuid=:recordId")
    Optional<KitchenOrder> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from KitchenOrder st where st.uuid=:recordId")
    Optional<KitchenOrder> deleteByUuid(@Param("recordId")UUID uuid);


    Optional<KitchenOrder> findByFoodName(String foodName);

    @Query("SELECT p FROM KitchenOrder p WHERE " +
            "p.foodName LIKE CONCAT('%',:query, '%')" +
            "Or p.dateCreated LIKE CONCAT('%', :query, '%')")
    List<KitchenOrder> searchKitchenOrderByFoodNameAndDateCreated(String query);


}
