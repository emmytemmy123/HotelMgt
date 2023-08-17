package fcmb.com.good.repo.transaction;

import fcmb.com.good.model.entity.transaction.ExpenseRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExpenseRequestRepository extends JpaRepository<ExpenseRequest, Long> {

    @Query("select st from ExpenseRequest st where st.uuid=:recordId")
    Optional<ExpenseRequest> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from ExpenseRequest st where st.uuid=:recordId")
    Optional<ExpenseRequest> deleteByUuid(@Param("recordId")UUID uuid);

}
