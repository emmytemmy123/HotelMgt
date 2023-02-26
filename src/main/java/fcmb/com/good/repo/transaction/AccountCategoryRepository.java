package fcmb.com.good.repo.transaction;

import fcmb.com.good.model.entity.transaction.AccountCategory;
import fcmb.com.good.model.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountCategoryRepository extends JpaRepository<AccountCategory, Long> {

    @Query("select st from AccountCategory st where st.uuid=:recordId")
    Optional<AccountCategory> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from AccountCategory st where st.uuid=:recordId")
    Optional<AccountCategory> deleteByUuid(@Param("recordId")UUID uuid);

    Optional<AccountCategory> findByCurrency(String currency);

}
