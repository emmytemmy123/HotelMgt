package fcmb.com.good.model.entity.user;

import fcmb.com.good.model.entity.BaseUser;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@MappedSuperclass
@Data
@Entity
@EntityListeners(BaseListener.class)
@Table (name = "users")
@AllArgsConstructor
public class AppUser extends BaseUser {

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "userTypeId", referencedColumnName = "id")
    private List<UserType> usertype = new ArrayList<UserType>();



    public AppUser(){}

   }
