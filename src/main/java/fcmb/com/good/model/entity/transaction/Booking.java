package fcmb.com.good.model.entity.transaction;


import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.entity.user.Customer;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@Entity
@EntityListeners(BaseListener.class)
@Table(name = "booking")
public class Booking extends BaseEntity {

    private String category;
    private String roomNo;
    private Double price;
    private Integer bookedNo;
    private Date checkInDate;
    private Date checkOutDate;
    private Integer night;
    private Double totalAmount;
    private String bookedBy;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customerId", insertable = true )
    private Customer customer;

    public Booking(){

    }

    }
