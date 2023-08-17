package fcmb.com.good.model.entity.user;

import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@EntityListeners(BaseListener.class)
@Entity
@Table(name = "userCategory")
public class UserCategory extends BaseEntity {

    private String name;
    private String description;


    public UserCategory(){

    }


}
