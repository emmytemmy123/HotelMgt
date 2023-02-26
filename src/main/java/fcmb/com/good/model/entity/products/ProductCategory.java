package fcmb.com.good.model.entity.products;


import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.entity.user.AppUser;
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
@Table(name = "productCategory")
public class ProductCategory extends BaseEntity {

    private String name;
    private String description;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "createdBy_id", updatable = true)
    private AppUser createdBy;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productCategory", cascade = CascadeType.MERGE)
    private Set<Product> prod = new HashSet<>();

    public ProductCategory(){}

}
