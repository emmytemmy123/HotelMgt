package fcmb.com.good.utills;

import fcmb.com.good.model.entity.transaction.Orders;
import fcmb.com.good.repo.transaction.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@RequiredArgsConstructor
public class OrderUtils {

//    private final OrdersRepository ordersRepository;


//    public String generateOrderNumber(){
//        String orderNumber;
//    Optional<Orders> listOrderForToday = ordersRepository.findOrderForCurrentDate();
//       Orders todayLastOrder = listOrderForToday.get();
//        if(listOrderForToday.isEmpty() || !listOrderForToday.isPresent())
//            return orderNumber = "1";
//        else {
//            orderNumber = todayLastOrder.getDateCreated().getYear() + todayLastOrder.getDateCreated().getMonthValue()
//                    + todayLastOrder.getDateCreated().getDayOfMonth() + todayLastOrder.getOrderNo();
//            return orderNumber;
//        }
//    }
}
