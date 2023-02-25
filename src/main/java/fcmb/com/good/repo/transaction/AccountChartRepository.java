package fcmb.com.good.repo.transaction;

import fcmb.com.good.model.entity.transaction.AccountChart;
import fcmb.com.good.model.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountChartRepository extends JpaRepository<AccountChart, Long> {

    @Query("select st from AccountChart st where st.uuid=:recordId")
    Optional<AccountChart> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from AccountChart st where st.uuid=:recordId")
    Optional<AccountChart> deleteByUuid(@Param("recordId")UUID uuid);

    Optional<AccountChart> findByAccountNo(Integer accountNo);


}
