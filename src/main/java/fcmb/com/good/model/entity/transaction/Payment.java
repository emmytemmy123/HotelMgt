package fcmb.com.good.model.entity.transaction;


import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.entity.services.SubServiceRequest;
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

    private Double amountPaid;
    private String description;
    private String paymentMode;
    private String paymentStatus;
    private String postedBy;
    private String tranReference;
    private Double totalAmount;
    private Double balance;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId", insertable = true, updatable = true)
    private Orders order;



    public Payment(){}


    }
