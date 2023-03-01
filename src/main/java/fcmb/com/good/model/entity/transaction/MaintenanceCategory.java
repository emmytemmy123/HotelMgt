package fcmb.com.good.model.entity.transaction;


import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.entity.products.Product;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.model.entity.user.Employee;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@EntityListeners(BaseListener.class)
@Entity
@Table(name = "maintenanceCategory")
public class MaintenanceCategory extends BaseEntity {

    private String name;
    private String description;
    private String postedBy;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "createdById", updatable = true)
    private AppUser createdBy;



    public MaintenanceCategory(){}

}
