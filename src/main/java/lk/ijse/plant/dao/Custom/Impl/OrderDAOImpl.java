package lk.ijse.plant.dao.Custom.Impl;

import lk.ijse.plant.dao.Custom.ItemDAO;
import lk.ijse.plant.dao.Custom.OrderDAO;
import lk.ijse.plant.dao.Custom.OrderItemDAO;
import lk.ijse.plant.dao.DAOFactory;
import lk.ijse.plant.dao.SQLUtil;
import lk.ijse.plant.entity.Order;
import lk.ijse.plant.entity.OrderItem;
import lk.ijse.plant.entity.PlaceOrder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    OrderItemDAO orderItemDAO = (OrderItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_ITEM);

    @Override
    public List<Order> getAll() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }


    public boolean add(Order entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO `Orders` (Order_id , Date, Price,Customer_id,User_id) VALUES (?,?,?,?,?)",entity.getOrder_id(), entity.getPrice(),entity.getCustomer_id(),entity.getDate(),entity.getUser_id());
    }

    @Override
    public boolean update(Order entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT Order_id FROM Orders ORDER BY Order_id DESC LIMIT 1;");
        return rst.next() ? String.format("OID-%03d", (Integer.parseInt(rst.getString("Order_id").replace("OID-", "")) + 1)) : "OID-001";
    }


    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Order search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

   /* @Override
    public boolean makeOrder(PlaceOrder placeOrder) {
        try {
            boolean isOrderSaved = add(placeOrder.getOrder());
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
    }*/
    }
