package lk.ijse.plant.bo.Custom.Impl;

import lk.ijse.plant.bo.Custom.PlaceOrderBO;
import lk.ijse.plant.dao.Custom.CustomerDAO;
import lk.ijse.plant.dao.Custom.ItemDAO;
import lk.ijse.plant.dao.Custom.OrderDAO;
import lk.ijse.plant.dao.Custom.OrderItemDAO;
import lk.ijse.plant.dao.DAOFactory;
import lk.ijse.plant.db.DBConnection;
import lk.ijse.plant.dto.CustomerDTO;
import lk.ijse.plant.dto.ItemDTO;
import lk.ijse.plant.dto.OrderDTO;
import lk.ijse.plant.dto.OrderItemDTO;
import lk.ijse.plant.entity.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PlaceOrderBOImpl implements PlaceOrderBO {
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PLACE_ORDER);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    OrderItemDAO orderItemDAO = (OrderItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_ITEM);
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);


    @Override
    public CustomerDTO searchCustomer(String nic) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.search(nic);
        return new CustomerDTO(customer.getCustomer_id(), customer.getCustomer_name(),customer.getContact(), customer.getNic(), customer.getAddress(),customer.getDate());
    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        Item item =itemDAO.search(code);
        return new ItemDTO(item.getItem_id(),item.getItem_name(),item.getQuantity(),item.getPrice(),item.getDescription(),item.getDate()) ;
    }

    @Override
    public boolean placeOrder(PlaceOrder placeOrder) throws SQLException, ClassNotFoundException {

        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isOrderSaved = orderDAO.add(placeOrder.getOrder());
            System.out.println("isOrderSaved = " + isOrderSaved);
            if (isOrderSaved) {

                boolean isQtyUpdated = false;

                for (OrderItem entity : placeOrder.getOdList()) {
                    isQtyUpdated = itemDAO.UpdateQty(entity);

                }
                System.out.println("isQtyUpdated = " + isQtyUpdated);


                if (isQtyUpdated) {

                    boolean isOrderDetailSaved = false;

                    for (OrderItem entity : placeOrder.getOdList()) {
                        isOrderDetailSaved = orderItemDAO.SaveItem(entity);
                    }
                    System.out.println("isOrderDetailSaved = " + isOrderDetailSaved);

                    if (isOrderDetailSaved) {
                        connection.commit();
                        return true;
                    } else {
                        connection.rollback();
                    }
                } else {
                    connection.rollback();
                }
            } else {
                connection.rollback();
            }

        } catch (Exception e) {
            connection.rollback();
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
        }
        return false;
    }


    @Override
    public String generateNewOrderID() throws SQLException,ClassNotFoundException{
        return orderDAO.generateNewID();
    }
}
