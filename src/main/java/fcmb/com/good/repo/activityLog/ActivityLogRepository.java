package fcmb.com.good.repo.activityLog;


import fcmb.com.good.model.entity.activityLog.ActivityLog;
import fcmb.com.good.model.entity.transaction.Orders;
import fcmb.com.good.model.entity.transaction.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {


    @Query("select st from ActivityLog st where st.uuid=:recordId")
    Optional<ActivityLog> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from ActivityLog st where st.uuid=:recordId")
    Optional<ActivityLog> deleteByUuid(@Param("recordId")UUID uuid);

    @Query("select st from ActivityLog st where st.category=:category")
    List<ActivityLog> findActivityLogByCategory(@Param("category") String category);

    @Query("SELECT st FROM ActivityLog st WHERE st.performedDate LIKE CONCAT('%',:query,'%') ")
    List<ActivityLog> findActivityLogByDate(String query);

//    @Query( value = "select * from ActivityLog where month(date_created) =:month and year(date_created) =:year order by id Desc limit 1", nativeQuery = true)
//    List<ActivityLog> findActivityLogByDate(@Param("month")Integer month, @Param("year") Integer year);

    @Query(value = "SELECT * FROM activityLog WHERE date_created BETWEEN :start_date AND :end_date", nativeQuery = true)
    List<ActivityLog> findByDateCreatedBetween ( String start_date, String end_date);


}
