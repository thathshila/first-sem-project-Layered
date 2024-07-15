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

    @Override
    public List<Order> getAll() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }


    public boolean add(Order entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO Orders (Order_id , Date, Price,Customer_id,User_id) VALUES (?,?,?,?,?)",entity.getOrder_id(),entity.getDate(), entity.getPrice(),entity.getCustomer_id(),entity.getUser_id());
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
        if (rst.next()) {
            String string = rst.getString(1);
            String idNumber = string.replaceAll("\\D", "");
            int id = 0;
            if (!idNumber.isEmpty()) {
                id = Integer.parseInt(idNumber);
            }
            id++;
            String ID = String.format("I%03d", id); // Format ID with leading zeros
            return ID;
        } else {
            return "I001";
        }
    }



    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Order search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
    }
