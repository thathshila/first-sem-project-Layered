package lk.ijse.plant.dao.Custom;

import lk.ijse.plant.dao.crudDAO;
import lk.ijse.plant.entity.OrderItem;

import java.sql.SQLException;
import java.util.List;

public interface OrderItemDAO extends crudDAO<OrderItem> {

    boolean SaveItem(OrderItem items) throws SQLException,ClassNotFoundException;
}
