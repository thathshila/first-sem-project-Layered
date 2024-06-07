package lk.ijse.plant.bo;


import lk.ijse.plant.bo.Custom.Impl.CustomerBOImpl;
import lk.ijse.plant.bo.Custom.Impl.SupplierBOImpl;

public class BOFactory {

    private static BOFactory boFactory;

    private BOFactory(){}

    public static BOFactory getBoFactory() {
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }
    public  enum BOTypes{
        CUSTOMER,SUPPLIER
    }

    public SuperBO getBO(BOTypes types){
        switch (types){
            case CUSTOMER:
                return new CustomerBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            default:
                return null;
        }
    }
}
