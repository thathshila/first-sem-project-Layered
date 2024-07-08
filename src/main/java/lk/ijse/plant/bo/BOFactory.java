package lk.ijse.plant.bo;


import lk.ijse.plant.bo.Custom.Impl.*;
import lk.ijse.plant.dao.Custom.Impl.OrderDAOImpl;

public class BOFactory { //object create

    private static BOFactory boFactory;

    private BOFactory(){} //constructor private nis nawatha nawatha object create krarann beeeee

    public static BOFactory getBoFactory() {
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }
    public  enum BOTypes{
        CUSTOMER,SUPPLIER,EMPLOYEE,ITEM,USER,PLACE_ORDER,ORDER
    }

    public SuperBO getBO(BOTypes types){
        switch (types){
            case CUSTOMER:
                return new CustomerBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case USER:
                return new UserBOImpl();
            case PLACE_ORDER:
                return new PlaceOrderBOImpl();
            default:
                return null;
        }
    }
}
