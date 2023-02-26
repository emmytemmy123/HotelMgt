package fcmb.com.good.repo.user;

import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.model.entity.user.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  /*  @Query("select st from Customer st where st.email=:email and st.password=:password")
    Optional<Customer> findCustomerByEmailAndPassword(@Param("email"),  @Param("password"));
*/

    Customer findByUsername(String username);

    @Query("select st from Customer st where st.uuid=:recordId")
    Optional<Customer> findByUuid(@Param("recordId")UUID uuid);

    @Query("select c from Customer c where c.email=:email")
    Optional<Customer> findByEmailId(@Param("email")String email);

    Customer findByEmail(String email);





}
