package fcmb.com.good.repo.user;


import fcmb.com.good.model.entity.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.UUID;


@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {


    @Query("select u from Users u where u.email=:email")
    Optional<Users> findByEmailId(@Param("email") String email);

    Users findByUsername(String username);

    Users findByEmail(String email);

    @Query("select st from Users st where st.uuid=:recordId")
    Optional<Users> findByUuid(@Param("recordId") UUID uuid);

    @Query("select st from Users st where st.name=:name")
    Optional<Users> findUsersByName(@Param("name") String name);

    //    @Query("select st from Users st where st.username=:username")
    Optional<Users> findUsersByUsername( String username);

    Optional<Users> findUsersByEmail( String email);

    @Query("select st from Users st where st.username=:username")
    Optional<Users> findUsersByUsername2(@Param("username") String username);




}
