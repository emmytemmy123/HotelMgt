package fcmb.com.good.model.entity.Events;


import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.entity.user.Users;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@EntityListeners(BaseListener.class)
@AllArgsConstructor
@Table(name = "events")
public class Events extends BaseEntity {

    private String name;
    private String description;
    private Integer capacity;
    private String status;
    private String photo;
    private String category;
    private Double price;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "eventsCategoryId", insertable = true)
    private EventsCategory eventsCategory;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "createdById", updatable = true)
    private Users createdBy;


    public Events(){

    }


}
