package fcmb.com.good.repo.products;

import fcmb.com.good.model.entity.products.ProductOrder;
import fcmb.com.good.model.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {

    @Query("select st from ProductOrder st where st.uuid=:recordId")
    Optional<ProductOrder> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from ProductOrder st where st.uuid=:recordId")
    Optional<ProductOrder> deleteByUuid(@Param("recordId")UUID uuid);

    Optional<ProductOrder> findByOrderNo(Integer orderNo);


}
