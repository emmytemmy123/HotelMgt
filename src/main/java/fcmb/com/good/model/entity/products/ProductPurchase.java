package fcmb.com.good.model.entity.products;


import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@EntityListeners(BaseListener.class)
@Entity
@Table(name = "productPurchase")
public class ProductPurchase extends BaseEntity {

    private String name;
    private String category;
    private String description;
    private String companyName;
    private String brand;
    private String quantity;
    private Double price;
    private Date productPurchaseDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "productId", insertable = true)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "createdBy", updatable = true, insertable=true)
    private AppUser createdBy;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "productCategory", updatable = true)
    private ProductType productCategory;

    public ProductPurchase(){}




}
