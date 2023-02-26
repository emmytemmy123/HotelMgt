package fcmb.com.good.model.entity.user;




import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@EntityListeners(BaseListener.class)
@Table(name = "roles")
@AllArgsConstructor
public class Role extends BaseEntity {

    private String department;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<AppUser> users = new ArrayList<AppUser>();

    public Role(){}

    }
