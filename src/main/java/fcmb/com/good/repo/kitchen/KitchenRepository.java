package fcmb.com.good.repo.kitchen;

import fcmb.com.good.model.entity.kitchen.Kitchen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface KitchenRepository extends JpaRepository<Kitchen, Long> {

    @Query("select st from Kitchen st where st.uuid=:recordId")
    Optional<Kitchen> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from Kitchen st where st.uuid=:recordId")
    Optional<Kitchen> deleteByUuid(@Param("recordId")UUID uuid);

    Optional<Kitchen> findByFoodName(String foodName);

    @Query("SELECT p FROM Kitchen p WHERE " +
            "p.foodName LIKE CONCAT('%',:query, '%')" +
            "Or p.description LIKE CONCAT('%', :query, '%')")
    List<Kitchen> searchKitchenByName(String query);

    @Query("SELECT p FROM Kitchen p WHERE " +
            "p.category LIKE CONCAT('%',:query, '%')" +
            "Or p.description LIKE CONCAT('%', :query, '%')")
    List<Kitchen> searchKitchenByCategory(String query);


}
