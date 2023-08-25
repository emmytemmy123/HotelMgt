package fcmb.com.good.model.entity.Events;


import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.entity.user.Users;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@EntityListeners(BaseListener.class)
@AllArgsConstructor
@Table(name = "eventOrder")
public class EventOrder extends BaseEntity {

    private String eventName;
    private String orderBy;
    private String phone;
    private String description;
    private Integer capacity;
    private String status;
    private String photo;
    private String category;
    private Double price;
    private String paymentStatus;
    private Date eventDay;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "eventsId", insertable = true)
    private Events events;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "createdById", updatable = true)
    private Users createdBy;


    public EventOrder(){}

}
