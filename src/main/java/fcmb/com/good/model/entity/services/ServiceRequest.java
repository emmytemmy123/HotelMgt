package fcmb.com.good.model.entity.services;

import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.entity.rooms.Rooms;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.model.entity.user.Customer;
import fcmb.com.good.model.entity.user.Employee;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@EntityListeners(BaseListener.class)
@Entity
@Table(name = "serviceRequest")
public class ServiceRequest extends BaseEntity {

    private String serviceName;
    private String serviceBy;
    private  Integer serviceRequestNo;
    private String currentCustomer;
    private String serviceCategory;
    private Double price;
    private String paymentStatus;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "subServiceId", insertable = true, updatable = true)
    private SubService subService;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "employeeId", insertable = true, updatable = true)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customerId", insertable = true, updatable = true)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "roomId", insertable = true, updatable = true)
    private Rooms rooms;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "createdById", insertable = true, updatable = true)
    private AppUser createdBy;


    public ServiceRequest(){}


   }
