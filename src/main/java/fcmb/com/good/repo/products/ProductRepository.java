package fcmb.com.good.repo.products;

import fcmb.com.good.model.entity.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select st from Product st where st.uuid=:recordId")
    Optional<Product> findByUuid(@Param("recordId") UUID uuid);

    @Query("delete from Product st where st.uuid=:recordId")
    Optional<Product> deleteByUuid(@Param("recordId")UUID uuid);

    Optional<Product> findByName(String name);

    @Query("SELECT p FROM Product p WHERE " +
            "p.name LIKE CONCAT('%',:query, '%')" +
            "Or p.category LIKE CONCAT('%', :query, '%')")
    List<Product> searchProductsByName(String query);

//    @Query("SELECT p FROM Product p WHERE " +
//            "Or p.description LIKE CONCAT('%', :query, '%')")
//    List<Product> searchProductsByProductCategory(String query);

}
