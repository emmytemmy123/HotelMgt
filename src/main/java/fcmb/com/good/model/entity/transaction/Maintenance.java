package fcmb.com.good.model.entity.transaction;


import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.entity.assets.Assets;
import fcmb.com.good.model.entity.kitchen.Kitchen;
import fcmb.com.good.model.entity.rooms.Rooms;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.model.entity.user.Customer;
import fcmb.com.good.model.entity.user.Employee;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@EntityListeners(BaseListener.class)
@Entity
@Table(name = "maintenance")
public class Maintenance extends BaseEntity {

    private String category;
    private String name;
    private String description;
    private String comment;
    private Integer quantity;
    private Double cost;
    private Double amount;
    private String status;
    private String requestedBy;
    private String maintainedBy;
    private Date dateMaintenance;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "createdById", insertable = true, updatable = true)
    private AppUser createdBy;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "employeeId", insertable = true, updatable = true)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "maintenanceCategoryId", insertable = true, updatable = true)
    private MaintenanceCategory maintenanceCategory;

//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "assetId", insertable = true, updatable = true)
//    private Assets assets;
//
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "kitchenId", insertable = true, updatable = true)
//    private Kitchen kitchen;
//
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "roomId", insertable = true, updatable = true)
//    private Rooms rooms;

    public Maintenance(){}

    }
