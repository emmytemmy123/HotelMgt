package fcmb.com.good.repo.user;

import fcmb.com.good.model.entity.user.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersCategoryRepository extends JpaRepository<UserCategory, Long> {


    @Query("select st from UserCategory st where st.uuid=:recordId")
    Optional<UserCategory> findByUuid(@Param("recordId") UUID uuid);

    Optional<UserCategory> findByName(String name);


}