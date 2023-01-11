package fcmb.com.good.repo.user;

import fcmb.com.good.model.entity.user.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Long> {

    @Query("select st from UserType st where st.uuid=:recordId")
    Optional<UserType> findByUuid(@Param("recordId") UUID uuid);

    @Query("select ut from UserType ut")
    List<UserType> getAllUserType();

}
