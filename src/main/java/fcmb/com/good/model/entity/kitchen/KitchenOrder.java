package fcmb.com.good.model.entity.kitchen;


import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.entity.rooms.Rooms;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.model.entity.user.Customer;
import fcmb.com.good.model.entity.user.Employee;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@AllArgsConstructor
@EntityListeners(BaseListener.class)
@Table(name ="kitchenOrder")
public class KitchenOrder extends BaseEntity {

    private String foodName;
    private String description;
    private String category;
    private Integer quantity;
    private Double price;
    private Integer requestNo;
    private String accountNo;
    private String salesPerson;
    private String status;
    private String currentCustomer;
    private Double totalAmount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "kitchenId", insertable = true, updatable = true)
    private Kitchen kitchen;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "kitchenCategoryId", insertable = true, updatable = true)
    private KitchenCategory kitchenCategory;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "createdById", insertable = true, updatable = true)
    private AppUser createdBy;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customerId", insertable = true, updatable = true)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "roomId", insertable = true, updatable = true)
    private Rooms rooms;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "employeeId", insertable = true, updatable = true)
    private Employee employee;


    public KitchenOrder(){}


}
