package fcmb.com.good.model.entity.transaction;

import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.entity.products.Product;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.model.entity.user.Customer;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@EntityListeners(BaseListener.class)
@Entity
@Table(name = "orderItems")
public class OrderItems extends BaseEntity {

    private String itemName;
    private Double salesPrice;
    private Integer quantity;
    private Double amount;
    private Double purchasePrice;
    private Double purchaseAmount;
    private Double profit;
    private String serviceName;
    private String description;
    private Date transactionDate;
    private String status;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "createdById", insertable = true, updatable = true)
    private AppUser createdBy;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId", insertable = true, updatable = true)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "productId", insertable = true, updatable = true)
    private Product product;

    public OrderItems(){}

}
