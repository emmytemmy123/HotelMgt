package fcmb.com.good.model.listener;

import fcmb.com.good.model.entity.Events.EventOrder;
import fcmb.com.good.model.entity.Events.Events;
import fcmb.com.good.model.entity.Events.EventsCategory;
import fcmb.com.good.model.entity.activityLog.ActivityLog;
import fcmb.com.good.model.entity.activityLog.ActivityLogCategory;
import fcmb.com.good.model.entity.others.DamagedAssets;
import fcmb.com.good.model.entity.others.Document;
import fcmb.com.good.model.entity.products.*;
import fcmb.com.good.model.entity.products.ProductFacility;
import fcmb.com.good.model.entity.services.SubServiceRequest;
import fcmb.com.good.model.entity.services.ServiceCategory;
import fcmb.com.good.model.entity.transaction.*;
import fcmb.com.good.model.entity.transaction.Orders;
import fcmb.com.good.model.entity.user.UserCategory;
import fcmb.com.good.model.entity.user.Users;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import java.util.UUID;

@Component
public class BaseListener {

    @PrePersist
    private void beforeCreate(Object data) {


        if(data instanceof Events){
            Events events = (Events) data;
            events.setUuid(UUID.randomUUID());
        }

        else if(data instanceof EventsCategory){
            EventsCategory eventsCategory = (EventsCategory) data;
            eventsCategory.setUuid(UUID.randomUUID());
        }

        else if(data instanceof EventOrder){
            EventOrder eventsOrder = (EventOrder) data;
            eventsOrder.setUuid(UUID.randomUUID());
        }


        else if(data instanceof DamagedAssets){
            DamagedAssets damagedAssets = (DamagedAssets) data;
            damagedAssets.setUuid(UUID.randomUUID());
        }
        else if(data instanceof Document){
            Document document = (Document) data;
            document.setUuid(UUID.randomUUID());
        }

        else if(data instanceof Orders){
            Orders productOrder = (Orders) data;
            productOrder.setUuid(UUID.randomUUID());
        }

        else if(data instanceof ProductPurchase){
            ProductPurchase productPurchase = (ProductPurchase) data;
            productPurchase.setUuid(UUID.randomUUID());
        }
        else if(data instanceof Product){
            Product product = (Product) data;
            product.setUuid(UUID.randomUUID());
        }

        else if(data instanceof SubServiceRequest){
            SubServiceRequest subServiceRequest = (SubServiceRequest) data;
            subServiceRequest.setUuid(UUID.randomUUID());
        }
        else if(data instanceof AccountCategory){
            AccountCategory accountCategory = (AccountCategory) data;
            accountCategory.setUuid(UUID.randomUUID());
        }
        else if(data instanceof AccountChart){
            AccountChart accountChart = (AccountChart) data;
            accountChart.setUuid(UUID.randomUUID());
        }

        else if(data instanceof BookingReminder){
            BookingReminder bookingReminder = (BookingReminder) data;
            bookingReminder.setUuid(UUID.randomUUID());
        }

        else if(data instanceof ExpenseRequest){
            ExpenseRequest expenseRequest = (ExpenseRequest) data;
            expenseRequest.setUuid(UUID.randomUUID());
        }

        else if(data instanceof Expenses){
            Expenses expenses = (Expenses) data;
            expenses.setUuid(UUID.randomUUID());
        }

        else if(data instanceof MaintenanceRequest){
          MaintenanceRequest maintenanceRequest = (MaintenanceRequest) data;
          maintenanceRequest.setUuid(UUID.randomUUID());
        }

        else if(data instanceof Payment){
            Payment payment = (Payment) data;
            payment.setUuid(UUID.randomUUID());
        }

        else if(data instanceof ProductCategory){
            ProductCategory productCategory = (ProductCategory) data;
            productCategory.setUuid(UUID.randomUUID());
        }

        else if(data instanceof ServiceCategory){
            ServiceCategory serviceCategory = (ServiceCategory) data;
            serviceCategory.setUuid(UUID.randomUUID());
        }

        else if(data instanceof ProductFacility){
            ProductFacility roomFacility = (ProductFacility) data;
            roomFacility.setUuid(UUID.randomUUID());
        }

        else if(data instanceof ExpenseCategory){
            ExpenseCategory expenseCategory = (ExpenseCategory) data;
            expenseCategory.setUuid(UUID.randomUUID());
        }

        else if(data instanceof MaintenanceCategory){
            MaintenanceCategory maintenanceCategory = (MaintenanceCategory) data;
            maintenanceCategory.setUuid(UUID.randomUUID());
        }


        else if(data instanceof OrderItems){
            OrderItems orderItems = (OrderItems) data;
            orderItems.setUuid(UUID.randomUUID());
        }

        else if(data instanceof Users){
            Users users = (Users) data;
            users.setUuid(UUID.randomUUID());
        }

        else if(data instanceof UserCategory){
            UserCategory userCategory = (UserCategory) data;
            userCategory.setUuid(UUID.randomUUID());
        }

        else if(data instanceof ActivityLogCategory){
            ActivityLogCategory activityLogCategory = (ActivityLogCategory) data;
            activityLogCategory.setUuid(UUID.randomUUID());
        }

        else if(data instanceof ActivityLog){
            ActivityLog activityLog = (ActivityLog) data;
            activityLog.setUuid(UUID.randomUUID());
        }



    }








}
