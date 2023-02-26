package fcmb.com.good.repo.services;

import fcmb.com.good.model.entity.services.ServiceRequest;
import fcmb.com.good.model.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {

    @Query("select st from ServiceRequest st where st.uuid=:recordId")
    Optional<ServiceRequest> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from ServiceRequest st where st.uuid=:recordId")
    Optional<ServiceRequest> deleteByUuid(@Param("recordId")UUID uuid);

    @Query("SELECT p FROM ServiceRequest p WHERE " +
            "p.serviceName LIKE CONCAT('%',:query, '%')")
    List<ServiceRequest> searchServiceRequestByName(String query);


}
