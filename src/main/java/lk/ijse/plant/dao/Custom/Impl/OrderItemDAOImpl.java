package lk.ijse.plant.dao.Custom.Impl;

import lk.ijse.plant.dao.Custom.OrderItemDAO;
import lk.ijse.plant.dao.SQLUtil;
import lk.ijse.plant.entity.OrderItem;

import java.sql.SQLException;
import java.util.List;

public class OrderItemDAOImpl implements OrderItemDAO {
    @Override
    public List<OrderItem> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    public boolean add(OrderItem entity) throws SQLException, ClassNotFoundException {
      return SQLUtil.execute("INSERT INTO Order_Item (Order_id,Item_id, Quantity, Price) VALUES (?,?,?,?)", entity.getOrder_id(),entity.getItem_id(),entity.getQuantity(),entity.getPrice());
    }

    @Override
    public boolean update(OrderItem entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderItem search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean SaveItem(OrderItem entity) throws SQLException,ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO Order_Item VALUES(?, ?, ?,?)",
                entity.getOrder_id(),
                entity.getItem_id(),
                entity.getQuantity(),
                entity.getPrice());
    }
}
