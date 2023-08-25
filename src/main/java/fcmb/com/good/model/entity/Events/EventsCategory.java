package fcmb.com.good.model.entity.Events;


import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.entity.user.Users;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@EntityListeners(BaseListener.class)
@Entity
@Table(name = "eventsCategory")
public class EventsCategory extends BaseEntity {

    private String name;
    private String description;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "createdById", updatable = true)
    private Users createdBy;

    public EventsCategory(){

    }



}
