package fcmb.com.good.model.entity.user;

import fcmb.com.good.model.entity.BaseUser;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@EntityListeners(BaseListener.class)
@Entity
@Table(name = "users")
public class Users extends BaseUser {

   private String usersCategory;
   private String designation;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "userCategoryId", updatable = true)
    private UserCategory userCategory;

    public Users(){
    }


}
