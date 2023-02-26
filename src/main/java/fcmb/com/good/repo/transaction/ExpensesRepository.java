package fcmb.com.good.repo.transaction;

import fcmb.com.good.model.entity.transaction.Expenses;
import fcmb.com.good.model.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, Long> {

    @Query("select st from Expenses st where st.uuid=:recordId")
    Optional<Expenses> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from Expenses st where st.uuid=:recordId")
    Optional<Expenses> deleteByUuid(@Param("recordId")UUID uuid);

    Optional<Expenses> findByName(String name);

    @Query("SELECT p FROM Expenses p WHERE " +
            "p.name LIKE CONCAT('%',:query, '%')" +
            "Or p.category LIKE CONCAT('%', :query, '%')" +
            "Or p.dateOfExpense LIKE CONCAT('%', :query, '%')")
    List<Expenses> searchExpenseByNameAndCategory (String query);


}
