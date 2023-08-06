package fcmb.com.good.repo.services;

import fcmb.com.good.model.entity.services.SubServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubServiceRequestRepository extends JpaRepository<SubServiceRequest, Long> {

    @Query("select st from SubServiceRequest st where st.uuid=:recordId")
    Optional<SubServiceRequest> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from SubServiceRequest st where st.uuid=:recordId")
    Optional<SubServiceRequest> deleteByUuid(@Param("recordId")UUID uuid);

}


