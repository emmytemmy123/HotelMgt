package fcmb.com.good.model.entity.user;


import fcmb.com.good.model.entity.BaseUser;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@EntityListeners(BaseListener.class)
@Table(name="customer")
@AllArgsConstructor
public class Customer extends BaseUser {

    private String nin;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "createdById", updatable = true)
    private AppUser createdBy;



    public Customer(){

    }


}
