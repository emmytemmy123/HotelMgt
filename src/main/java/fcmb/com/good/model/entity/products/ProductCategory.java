package fcmb.com.good.model.entity.products;


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
@Table(name = "productType")
public class ProductCategory extends BaseEntity {

    private String room;
    private String items;
    private String description;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "createdById", updatable = true)
    private Users createdBy;


    public ProductCategory(){}

}
