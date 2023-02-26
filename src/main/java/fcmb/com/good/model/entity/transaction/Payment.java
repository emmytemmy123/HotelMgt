package fcmb.com.good.model.entity.transaction;


import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.entity.kitchen.Kitchen;
import fcmb.com.good.model.entity.products.Product;
import fcmb.com.good.model.entity.rooms.Rooms;
import fcmb.com.good.model.entity.services.SubService;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.model.entity.user.Customer;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@EntityListeners(BaseListener.class)
@Entity
@Table(name = "payment")
public class Payment extends BaseEntity {

    private String category;
    private Double amount;
    private String serviceName;
    private Integer accountNo;
    private Integer roomNo;
    private Double price;
    private Integer quantity;
    private String paymentStatus;
    private String paymentDetails;
    private String paidBy;

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
    @JoinColumn(name = "productId", insertable = true, updatable = true)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "kitchenId", insertable = true, updatable = true)
    private Kitchen kitchen;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "subServiceId", insertable = true, updatable = true)
    private SubService subService;


    public Payment(){}

    }
