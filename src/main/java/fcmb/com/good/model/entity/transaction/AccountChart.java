package fcmb.com.good.model.entity.transaction;


import fcmb.com.good.model.entity.BaseEntity;
import fcmb.com.good.model.entity.user.Users;
import fcmb.com.good.model.listener.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@EntityListeners(BaseListener.class)
@Entity
@Table(name = "accountChart")

public class AccountChart extends BaseEntity {

    private String category;
    private String code;
    private String accountName;
    private Integer accountNo;
    private Double balance;
    private String currentCustomer;



    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "createdById", updatable = true)
    private Users createdBy;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "accountCategoryId", insertable = true, updatable = true)
    private AccountCategory accountCategory;

    public AccountChart(){

    }

    }
