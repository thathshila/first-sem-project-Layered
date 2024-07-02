package lk.ijse.plant.dao.Custom;

import lk.ijse.plant.dao.crudDAO;
import lk.ijse.plant.entity.Item;
import lk.ijse.plant.entity.OrderItem;

import java.sql.SQLException;
import java.util.List;


public interface ItemDAO extends crudDAO<Item> {

    boolean UpdateQty(OrderItem items) throws SQLException;

    List<String> getItemName() throws SQLException;

}
