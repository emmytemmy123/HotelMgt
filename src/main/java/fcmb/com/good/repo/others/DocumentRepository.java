package fcmb.com.good.repo.others;

import fcmb.com.good.model.entity.others.Document;
import fcmb.com.good.model.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query("select st from  Document st where st.uuid=:recordId")
    Optional< Document> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from  Document st where st.uuid=:recordId")
    Optional< Document> deleteByUuid(@Param("recordId")UUID uuid);

    Optional<Document> findByName(String name);

}
