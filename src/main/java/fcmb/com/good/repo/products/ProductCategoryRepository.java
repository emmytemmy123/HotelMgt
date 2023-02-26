package fcmb.com.good.repo.products;

import fcmb.com.good.model.entity.products.ProductCategory;
import fcmb.com.good.model.entity.rooms.RoomCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Query("select st from ProductCategory st where st.uuid=:recordId")
    Optional<ProductCategory> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from ProductCategory st where st.uuid=:recordId")
    Optional<ProductCategory> deleteByUuid(@Param("recordId")UUID uuid);

    @Query("select st from ProductCategory st where st.name=:name")
    Optional<ProductCategory> findProductCategoryByName(@Param("name") String name);
}
