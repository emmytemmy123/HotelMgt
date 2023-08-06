package fcmb.com.good.repo.user;


import fcmb.com.good.model.entity.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<AppUser,Long> {


    @Query("select u from AppUser u where u.email=:email")
    Optional<AppUser> findByEmailId(@Param("email") String email);

    Optional<AppUser> findByUsername(String username);

    AppUser findByEmail(String email);

    @Query("select st from AppUser st where st.uuid=:recordId")
    Optional<AppUser> findByUuid(@Param("recordId") UUID uuid);

    @Query("select st from AppUser st where st.name=:name")
    Optional<AppUser> findUsersByName(@Param("name") String name);




}
