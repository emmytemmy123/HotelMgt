package fcmb.com.good.repo.transaction;


import fcmb.com.good.model.entity.transaction.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Long> {

    @Query("select st from ExpenseCategory st where st.uuid=:recordId")
    Optional<ExpenseCategory> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from ExpenseCategory st where st.uuid=:recordId")
    Optional<ExpenseCategory> deleteByUuid(@Param("recordId")UUID uuid);

    @Query("select st from ExpenseCategory st where st.name=:category")
    Optional<ExpenseCategory> findByName(@Param("category") String category);


}
