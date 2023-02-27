package fcmb.com.good.model.entity.transaction;


import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.model.entity.user.Customer;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@EntityListeners(BaseListener.class)
@Entity
@Table(name = "order")
public class Order extends BaseEntity {

    private Integer orderNo;
    private String orderBy;
    private Double amount;
    private Double amountDue;
    private String orderStatus;
    private Timestamp startTime;
    private Timestamp endTime;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customerId", insertable = true, updatable = true)
    private Customer customer;

    public Order(){}


}
