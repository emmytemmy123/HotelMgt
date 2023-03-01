package fcmb.com.good.repo.orderItem;

import fcmb.com.good.model.entity.transaction.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepo extends JpaRepository<OrderItems,Long> {
    //SELECT u FROM User u WHERE u.status = 1"
    @Query(value = "SELECT st FROM OrderItems st WHERE st.order.customer.id.name=:name")
    List<OrderItems> listOrderItemsByCustomerName(@RequestParam("name") String customerName);


}
