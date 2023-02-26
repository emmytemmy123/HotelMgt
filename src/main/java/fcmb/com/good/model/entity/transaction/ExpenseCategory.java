package fcmb.com.good.model.entity.transaction;


import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@EntityListeners(BaseListener.class)
@Entity
@Table(name = "expenseCategory")
public class ExpenseCategory extends BaseEntity {

    private String name;
    private String description;
    private String postedBy;

    public ExpenseCategory(){}

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "createdById", insertable = true, updatable = true)
    private AppUser createdBy;

//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "expenseId", insertable = true, updatable = true)
//    private Expenses expenses;
//
////    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
////    @JoinColumn(name = "expenseRequestId", insertable = true, updatable = true)
////    private ExpenseRequest expenseRequest;


}
