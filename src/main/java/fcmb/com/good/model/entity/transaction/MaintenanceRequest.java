package fcmb.com.good.model.entity.transaction;


import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.entity.user.Users;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@EntityListeners(BaseListener.class)
@Entity
@Table(name = "maintenanceRequest")
public class MaintenanceRequest extends BaseEntity {

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
    private String location;
    private LocalDateTime dateMaintenance;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "createdById", insertable = true, updatable = true)
    private Users createdBy;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "maintenanceCategoryId", insertable = true, updatable = true)
    private MaintenanceCategory maintenanceCategory;


    public MaintenanceRequest(){}

    }
