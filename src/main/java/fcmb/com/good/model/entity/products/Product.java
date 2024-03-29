package fcmb.com.good.model.entity.products;


import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@AllArgsConstructor
@EntityListeners(BaseListener.class)
@Entity
@Table(name = "products")
@Data
public class Product extends BaseEntity {

    private String category;
    private String brand;
    private String name;
    private String room;
    private String productStatus;
    private String description;
    private Integer quantity;
    private Double salesPrice;
    private Double purchasePrice;
    private Date expDate;
    private String postedBy;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "productTypeId", updatable = true)
    private ProductCategory productCategory;

    @OneToMany(mappedBy = "product")
    private List<ProductFacility> productFacilityList;

//    @OneToMany(mappedBy = "product")
//    private List<ServiceCategory> subServiceList;

    public Product(){}


}
