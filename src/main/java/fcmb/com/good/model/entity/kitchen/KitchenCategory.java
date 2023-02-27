package fcmb.com.good.model.entity.kitchen;

import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.entity.products.Product;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.model.entity.user.Customer;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import javax.persistence.*;
import java.util.List;


@Data
@AllArgsConstructor
@Entity
@EntityListeners(BaseListener.class)
@Table(name="kitchenCategory")
public class KitchenCategory extends BaseEntity {

    private String categoryName;
    private String description;

    @OneToMany(mappedBy = "kitchenCategory")
    private List<Kitchen> kitchenList;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "createdById", updatable = true)
    private AppUser createdBy;

    public KitchenCategory(){}


}
