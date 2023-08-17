package fcmb.com.good.repo.services;


import fcmb.com.good.model.entity.services.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface SubServiceRepository extends JpaRepository<ServiceCategory, Long> {

//    @Query("select st from ServiceCategory st where st.product.uuid=:productUuid")
//    List<ServiceCategory> findByRooms(@Param("productUuid") UUID productUuid);

//    @Query("select st from ServiceCategory st where st.customer.uuid=:customerUuid and st.product.uuid=:productUuid")
//    List<ServiceCategory> findSubServiceByCustomerAndRoom(@Param("customerUuid") UUID customerUuid, @Param("productUuid")UUID productUuid);

    @Query("select st from ServiceCategory st where st.uuid=:recordId")
    Optional<ServiceCategory> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from ServiceCategory st where st.uuid=:recordId")
    Optional<ServiceCategory> deleteByUuid(@Param("recordId")UUID uuid);

    @Query("select st from ServiceCategory st where st.serviceName=:recordId")
    Optional<ServiceCategory> findByName(@Param("recordId") String serviceName);

    @Query("SELECT st FROM ServiceCategory st WHERE " +
            "st.serviceName LIKE CONCAT('%',:query, '%')" )
    List<ServiceCategory> searchSubServiceByName(String query);



}
