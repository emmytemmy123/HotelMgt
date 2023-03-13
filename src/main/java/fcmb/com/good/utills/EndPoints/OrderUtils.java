package fcmb.com.good.utills.EndPoints;

import fcmb.com.good.model.entity.transaction.Orders;
import fcmb.com.good.repo.transaction.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;


public class OrderUtils {
    @Autowired
    static OrdersRepository ordersRepository;
    //FORMULAR: year+month+day-orderNo,
    //
    //fetch the last order of the that day, if return empty the oderNo wiil be 1
    //select * from order when the date is = currentdate, order by id in desc limit 1
    //if exist, exstract orderNo, convert to integer and set back as orderNo,

    public static String generateOrderNumber(){
        String orderNumber;
    Optional<Orders> listOrderForToday = ordersRepository.findOrderForCurrentDate();
       Orders todayLastOrder = listOrderForToday.get();
        if(listOrderForToday.isEmpty() || !listOrderForToday.isPresent())
            return orderNumber= "1";
        else {
            orderNumber = todayLastOrder.getDateCreated().getYear() + todayLastOrder.getDateCreated().getMonthValue()
                    + todayLastOrder.getDateCreated().getDayOfMonth() + todayLastOrder.getOrderNo();
            return orderNumber;
        }
    }
}
