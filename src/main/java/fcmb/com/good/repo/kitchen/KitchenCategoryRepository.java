package fcmb.com.good.repo.kitchen;


import fcmb.com.good.model.entity.kitchen.Kitchen;
import fcmb.com.good.model.entity.kitchen.KitchenCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface KitchenCategoryRepository extends JpaRepository<KitchenCategory, Long> {

    @Query("select st from KitchenCategory st where st.uuid=:recordId")
    Optional<KitchenCategory> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from KitchenCategory st where st.uuid=:recordId")
    Optional<KitchenCategory> deleteByUuid(@Param("recordId")UUID uuid);


    Optional<KitchenCategory> findByCategoryName(String categoryName);

    @Query("SELECT p FROM KitchenCategory p WHERE " +
            "p.categoryName LIKE CONCAT('%',:query, '%')")
    List<KitchenCategory> searchKitchenCategoryByName(String query);


}
