package fcmb.com.good.model.entity.services;

import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.entity.transaction.Orders;
import fcmb.com.good.model.entity.user.Customer;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@EntityListeners(BaseListener.class)
@Entity
@Table(name = "serviceRequest")
public class SubServiceRequest extends BaseEntity {

    private String serviceName;
    private String roomNo;
    private Integer noOfOccupant;
    private Double price;
    private String customerName;
    private String status;
    private String orderNo;



    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "subServiceId", insertable = true, updatable = true)
    private SubService subService;


    public SubServiceRequest(){}


    }
