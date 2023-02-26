package fcmb.com.good.model.entity.assets;


import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.entity.rooms.Rooms;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@EntityListeners(BaseListener.class)
@AllArgsConstructor
@Table(name = "assets")
public class Assets extends BaseEntity {

    private String name;
    private Double purchasePrice;
    private String description;
    private String code;
    private Integer quantity;
    private String status;
    private String photo;
//    private String recordId;
//    private String recordType;
//    private String Location;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "createdById", insertable = true, updatable = true)
    private AppUser createdBy;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "assetsCategoryId", insertable = true)
    private AssetsCategory assetsCategory;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "locationRoomId", insertable = true)
    private Rooms rooms;


    public Assets (){

    }


}
