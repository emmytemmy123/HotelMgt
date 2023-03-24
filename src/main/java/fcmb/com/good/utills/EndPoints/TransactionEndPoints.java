package fcmb.com.good.utills.EndPoints;

public class TransactionEndPoints {

    public static final String transaction="transaction";

    public static final String ACCOUNT_CATEGORY="/accountCategory";
    public static final String FIND_ACCOUNT_CATEGORY=ACCOUNT_CATEGORY+"/list";
    public static final String ADD_ACCOUNT_CATEGORY=ACCOUNT_CATEGORY+"/add";
    public static final String FIND_ACCOUNT_CATEGORY_BY_ID=ACCOUNT_CATEGORY+"/{id}";
    public static final String UPDATE_ACCOUNT_CATEGORY= ACCOUNT_CATEGORY+"/update/{id}";
    public static final String DELETE_ACCOUNT_CATEGORY= ACCOUNT_CATEGORY+"/customerDelete/{id}";

    public static final String ACCOUNT_CHART="/accountChart";
    public static final String FIND_ACCOUNT_CHART=ACCOUNT_CHART+"/list";
    public static final String ADD_ACCOUNT_CHART= ACCOUNT_CHART+"/add";
    public static final String FIND_ACCOUNT_CHART_BY_ID= ACCOUNT_CHART+"/{id}";
    public static final String UPDATE_ACCOUNT_CHART= ACCOUNT_CHART+"/update/{id}";
    public static final String DELETE_ACCOUNT_CHART= ACCOUNT_CHART+"/delete/{id}";

    public static final String EXPENSE_CATEGORY="/expenseCategory";
    public static final String FIND_EXPENSE_CATEGORY=EXPENSE_CATEGORY+"/list";
    public static final String ADD_EXPENSE_CATEGORY= EXPENSE_CATEGORY+"/add";
    public static final String FIND_EXPENSE_CATEGORY_BY_ID= EXPENSE_CATEGORY+"/{id}";
    public static final String UPDATE_EXPENSE_CATEGORY= EXPENSE_CATEGORY+"/update/{id}";
    public static final String DELETE_EXPENSE_CATEGORY= EXPENSE_CATEGORY+"/delete/{id}";

    public static final String BOOKING="/booking";
    public static final String FIND_BOOKING=BOOKING+"/list";
    public static final String ADD_BOOKING= BOOKING+"/add";
    public static final String FIND_BOOKING_BY_ID= BOOKING+"/{id}";
    public static final String UPDATE_BOOKING= BOOKING+"/update/{id}";
    public static final String DELETE_BOOKING= BOOKING+"/delete/{id}";

    public static final String MAINTENANCE_CATEGORY="/maintenanceCategory";
    public static final String FIND_MAINTENANCE_CATEGORY=MAINTENANCE_CATEGORY+"/list";
    public static final String ADD_MAINTENANCE_CATEGORY= MAINTENANCE_CATEGORY+"/add";
    public static final String FIND_MAINTENANCE_CATEGORY_BY_ID= MAINTENANCE_CATEGORY+"/{id}";
    public static final String UPDATE_MAINTENANCE_CATEGORY= MAINTENANCE_CATEGORY+"/update/{id}";
    public static final String DELETE_MAINTENANCE_CATEGORY= MAINTENANCE_CATEGORY+"/delete/{id}";

    public static final String EXPENSES="/expenses";
    public static final String FIND_EXPENSES=EXPENSES+"/list";
    public static final String ADD_EXPENSES= EXPENSES+"/add";
    public static final String FIND_EXPENSES_BY_ID= EXPENSES+"/{id}";
    public static final String UPDATE_EXPENSES= EXPENSES+"/update/{id}";
    public static final String DELETE_EXPENSES= EXPENSES+"/delete/{id}";
    public static final String FIND_EXPENSES_BY_NAME_AND_CATEGORY= EXPENSES+"/searchExpenseByNameAndCategory";

    public static final String MAINTENANCE="/maintenanceRequest";
    public static final String FIND_MAINTENANCE=MAINTENANCE+"/list";
    public static final String ADD_MAINTENANCE= MAINTENANCE+"/add";
    public static final String FIND_MAINTENANCE_BY_ID= MAINTENANCE+"/{id}";
    public static final String UPDATE_MAINTENANCE= MAINTENANCE+"/update/{id}";
    public static final String DELETE_MAINTENANCE= MAINTENANCE+"/delete/{id}";

    public static final String PAYMENT="/payment";
    public static final String FIND_PAYMENT=PAYMENT+"/list";
    public static final String ADD_PAYMENT= PAYMENT+"/add";
//  public static final String FIND_PAYMENT_BY_ID= PAYMENT+"/{id}";
    public static final String FIND_PAYMENT_BY_ORDER_ID = PAYMENT+"/findPaymentByOrderId";
    public static final String UPDATE_PAYMENT= PAYMENT+"/update/{id}";
    public static final String DELETE_PAYMENT= PAYMENT+"/delete/{id}";
    public static final String FIND_LIST_OF_PAYMENT_BY_DATE_RANGE= PAYMENT+"/findPaymentByDateRange";
    public static final String FIND_LISTS_OF_PAYMENT_BY_DATE= PAYMENT+"/findPaymentByDate";
    public static final String FIND_PAYMENT_BY_SALES_PERSON= PAYMENT+"/findPaymentBySalesPerson";
    public static final String FIND_PAYMENT_BY_CUSTOMER= PAYMENT+"/findPaymentByCustomer";

    public static final String PAYMENT_CATEGORY="/paymentCategory";
    public static final String FIND_PAYMENT_CATEGORY=PAYMENT_CATEGORY+"/list";
    public static final String ADD_PAYMENT_CATEGORY= PAYMENT_CATEGORY+"/add";
    public static final String FIND_PAYMENT_CATEGORY_BY_ID= PAYMENT_CATEGORY+"/{id}";
    public static final String UPDATE_PAYMENT_CATEGORY= PAYMENT_CATEGORY+"/update/{id}";
    public static final String DELETE_PAYMENT_CATEGORY= PAYMENT_CATEGORY+"/delete/{id}";

    public static final String ORDER_ITEMS="/orderItems";
    public static final String FIND_ORDER_ITEMS=ORDER_ITEMS+"/list";
//    public static final String ADD_ORDER_ITEMS= ORDER_ITEMS+"/add";
    public static final String FIND_ORDER_ITEMS_BY_ID= ORDER_ITEMS+"/{id}";
//    public static final String UPDATE_ORDER_ITEMS= ORDER_ITEMS+"/update/{id}";
//    public static final String DELETE_ORDER_ITEMS= ORDER_ITEMS+"/delete/{id}";

    public static final String ORDER="/order";
    public static final String FIND_ORDER=ORDER+"/list";
    public static final String ADD_ORDER= ORDER+"/add";
    public static final String FIND_ORDER_BY_ID= ORDER+"/{id}";
    public static final String UPDATE_ORDER= ORDER+"/update/{id}";
    public static final String DELETE_ORDER= ORDER+"/delete/{id}";
    public static final String FIND_ORDER_BY_CUSTOMER= ORDER+"/findOrderByCustomer";
    public static final String FIND_LISTS_OF_ORDER_BY_DATE= ORDER+"/findOrderByDate";




}
