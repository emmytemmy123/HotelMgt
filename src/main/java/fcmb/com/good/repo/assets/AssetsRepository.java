package fcmb.com.good.repo.assets;

import fcmb.com.good.model.entity.assets.Assets;
import fcmb.com.good.model.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssetsRepository extends JpaRepository<Assets, Long> {

    @Query("select st from Assets st where st.uuid=:recordId")
    Optional<Assets> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from Assets st where st.uuid=:recordId")
    Optional<Assets> deleteByUuid(@Param("recordId")UUID uuid);

    @Query("select st from Assets st where st.name=:recordId")
    Optional<Assets> findAssetByName(@Param("recordId") String name);


}
