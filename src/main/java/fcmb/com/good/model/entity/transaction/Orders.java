package fcmb.com.good.model.entity.transaction;


import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.entity.products.Product;
import fcmb.com.good.model.entity.user.Customer;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@EntityListeners(BaseListener.class)
@Entity
@Table(name = "orders")
public class Orders extends BaseEntity {

    private String orderNo;
    private String orderBy;
    private Double amount;
    private Double amountDue;
    private String productStatus;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer serialNo;
    private String roomStatus;



    @ManyToOne
    @JoinColumn(name = "customerId", insertable = true, updatable = true)
    private Customer customer;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrderItems> orderItemsList;

    @ManyToOne
    @JoinColumn(name = "productId", insertable = true, updatable = true)
    private Product product;




    public Orders(){}


}
