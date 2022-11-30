package fcmb.com.good.repo.user;

import fcmb.com.good.model.entity.user.Customer;
import fcmb.com.good.model.entity.user.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select st from Employee st where st.uuid=:recordId")
    Optional<Employee> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from Employee st where st.uuid=:recordId")
    Optional<Employee> deleteByUuid(@Param("recordId")UUID uuid);

}
