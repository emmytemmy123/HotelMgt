package fcmb.com.good.model.entity.products;


import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.entity.user.Customer;
import fcmb.com.good.model.entity.user.Employee;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;


@Data
@AllArgsConstructor
@EntityListeners(BaseListener.class)
@Entity
@Table(name = "productOrder")
public class ProductOrder extends BaseEntity {

    private String productName;
    private Double amount;
    private Double tax;
    private Integer quantity;
    private Double price;
    private Integer orderNo;
    private String accountNo;
    private Double profit;
    private String salesPerson;
    private String orderState;
    private String currentCustomer;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "employeeId",insertable = true, updatable = true)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customerId",insertable = true, updatable = true)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "productId", insertable = true, updatable = true)
    private Product product;

    public ProductOrder(){}


}
