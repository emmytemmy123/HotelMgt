package fcmb.com.good.repo.assets;

import fcmb.com.good.model.entity.assets.AssetsCategory;
import fcmb.com.good.model.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssetsCategoryRepository extends JpaRepository<AssetsCategory, Long> {

    @Query("select st from AssetsCategory st where st.uuid=:recordId")
    Optional<AssetsCategory> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from AssetsCategory st where st.uuid=:recordId")
    Optional<AssetsCategory> deleteByUuid(@Param("recordId")UUID uuid);

    @Query("select st from AssetsCategory st where st.name=:recordId")
    Optional<AssetsCategory> findProductCategoryByName(@Param("recordId") String name);

}
