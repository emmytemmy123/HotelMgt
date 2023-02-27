package fcmb.com.good.repo.products;

import fcmb.com.good.model.entity.products.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductType, Long> {

    @Query("select st from ProductType st where st.uuid=:recordId")
    Optional<ProductType> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from ProductType st where st.uuid=:recordId")
    Optional<ProductType> deleteByUuid(@Param("recordId")UUID uuid);

    @Query("select st from ProductType st where st.room=:name and st.items=:items ")
    Optional<ProductType> findProductCategoryByRoomAndItems(@Param("name") String room,@Param("items") String items );
}
