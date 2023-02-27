package fcmb.com.good.repo.orderItem;

import fcmb.com.good.model.entity.transaction.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepo extends JpaRepository<OrderItems,Long> {

}
