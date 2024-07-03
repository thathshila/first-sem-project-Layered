package lk.ijse.plant.dao;

import lk.ijse.plant.bo.Custom.Impl.PlaceOrderBOImpl;
import lk.ijse.plant.dao.Custom.Impl.*;


public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory(){}

    public static DAOFactory getDaoFactory(){
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes{
        CUSTOMER, SUPPLIER, EMPLOYEE, ITEM, USER, PLACE_ORDER, QUERY_DAO, ORDER_ITEM
    }

    public SuperDAO getDAO(DAOTypes types){
        switch (types){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case USER:
                return new UserDAOImpl();
            case PLACE_ORDER:
                return new OrderDAOImpl(); // This should correspond to PLACE_ORDER
            case ORDER_ITEM:
                return new OrderItemDAOImpl(); // This should be OrderItemDAOImpl
            case QUERY_DAO:
                return new QueryDAOImpl();
            default:
                return null;
        }
    }
}