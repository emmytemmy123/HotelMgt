package fcmb.com.good.model.entity.products;


import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;


@AllArgsConstructor
@EntityListeners(BaseListener.class)
@Entity
@Table(name = "products")
@Data
public class Product extends BaseEntity {

    private String name;
    private String description;
    private Integer quantity;
    private Double price;
    private Double purchasedPrice;
    private String code;
    private String location;
    private String status;
    private Double profit;
    private String productsCategory;
    private String photo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "createdBy_id", updatable = true)
    private AppUser createdBy;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_category_id", updatable = true)
    private ProductCategory productCategory;

    public Product(){}


}
