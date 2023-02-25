package fcmb.com.good.model.entity.kitchen;

import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.model.entity.user.Customer;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;


@Data
@Entity
@AllArgsConstructor
@EntityListeners(BaseListener.class)
@Table(name="kitchen")
public class Kitchen extends BaseEntity {

    private String foodName;
    private String description;
    private Integer quantity;
    private Double price;
    private String status;
    private String category;
    private String photo;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "kitchenCategoryId", insertable = true, updatable = true)
    private KitchenCategory kitchenCategory;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "createdById", updatable = true)
    private AppUser createdBy;



    public Kitchen(){}

}
