package fcmb.com.good.repo.user;


import fcmb.com.good.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    @Query("select u from User u where u.email=:email")
    Optional<User> findByEmailId(@Param("email") String email);

    User findByUsername(String username);

    User findByEmail(String email);

    @Query("select st from User st where st.uuid=:recordId")
    Optional<User> findByUuid(@Param("recordId") UUID uuid);




}
