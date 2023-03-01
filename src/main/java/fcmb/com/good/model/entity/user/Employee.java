package fcmb.com.good.model.entity.user;


import fcmb.com.good.model.entity.BaseUser;
import fcmb.com.good.model.entity.transaction.ExpenseRequest;
import fcmb.com.good.model.entity.transaction.Maintenance;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@EntityListeners(BaseListener.class)
@Entity
@Table(name = "employee")
public class Employee extends BaseUser {

    private String designation;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "createdById", updatable = true)
    private AppUser createdBy;

    @OneToMany(mappedBy = "employee")
    private List<ExpenseRequest> expenseRequestList;

    @OneToMany(mappedBy = "employee")
    private List<EmployeeShift>  employeeShiftList;

    @OneToMany(mappedBy = "employee")
    private List<Maintenance>  maintenanceRequestList;


    public Employee(){}


    }
