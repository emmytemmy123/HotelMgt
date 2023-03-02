package fcmb.com.good.repo.services;


import fcmb.com.good.model.entity.products.Product;
import fcmb.com.good.model.entity.services.Services;
import fcmb.com.good.model.entity.services.SubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface SubServiceRepository extends JpaRepository<SubService, Long> {

    @Query("select st from SubService st where st.product.uuid=:productUuid")
    List<SubService> findByRooms(@Param("productUuid") UUID productUuid);

    @Query("select st from SubService st where st.customer.uuid=:customerUuid and st.product.uuid=:productUuid")
    List<SubService> findSubServiceByCustomerAndRoom(@Param("customerUuid") UUID customerUuid, @Param("productUuid")UUID productUuid);

    @Query("select st from SubService st where st.uuid=:recordId")
    Optional<SubService> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from SubService st where st.uuid=:recordId")
    Optional<SubService> deleteByUuid(@Param("recordId")UUID uuid);

    @Query("select st from SubService st where st.serviceName=:recordId")
    Optional<SubService> findByName(@Param("recordId") String serviceName);

    @Query("SELECT st FROM SubService st WHERE " +
            "st.serviceName LIKE CONCAT('%',:query, '%')" )
    List<SubService> searchSubServiceByName(String query);



}
