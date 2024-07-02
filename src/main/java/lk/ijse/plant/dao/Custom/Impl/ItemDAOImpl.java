package lk.ijse.plant.dao.Custom.Impl;

import lk.ijse.plant.dao.Custom.ItemDAO;
import lk.ijse.plant.dao.SQLUtil;
import lk.ijse.plant.entity.Item;
import lk.ijse.plant.entity.OrderItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public List<Item> getAll() throws SQLException, ClassNotFoundException {
        List<Item> items = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM Items");
        while (rst.next()) {
            Item item = new Item(rst.getString("Item_id"), rst.getString("Item_name"), rst.getInt("Quantity"), rst.getDouble("Price"), rst.getString("Description"), rst.getDate("Date"));
            items.add(item);
        }
        return items;
    }

    public boolean add(Item entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO Items(Item_id,Item_name,Quantity,Price,Description,Date) VALUES(?,?,?,?,?,?)",
                entity.getItem_id(),
                entity.getItem_name(),
                entity.getQuantity(),
                entity.getPrice(),
                entity.getDescription(),
                entity.getDate());
    }

    @Override
    public boolean update(Item entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE Items SET Item_name = ?, Quantity = ?, Price = ?, Description = ?, Date = ? WHERE Item_id = ?",
                entity.getItem_name(),
                entity.getQuantity(),
                entity.getPrice(),
                entity.getDescription(),
                entity.getDate(),
                entity.getItem_id());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM Items WHERE Item_id = ?", id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT Item_id FROM Items ORDER BY Item_id DESC LIMIT 1");
        if (rst.next()) {
            String string = rst.getString(1);
            String[] strings = string.split("I0");
            int id = Integer.parseInt(strings[1]);
            id++;
            String ID = String.valueOf(id);
            int length = ID.length();
            if (length < 2) {
                return "I00" + id;
            } else {
                if (length < 3) {
                    return "I0" + id;
                } else {
                    return "I" + id;
                }
            }
        } else {
            return "I001";
        }
    }

    @Override
    public boolean exist(String code) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT Item_id FROM Items WHERE Item_id = ?", code);
        return rst.next();
    }

    @Override
    public Item search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Items WHERE Item_name = ?");
        rst.next();
        return new Item(rst.getString("Item_id"), rst.getString("Item_name"), rst.getInt("Quantity"), rst.getDouble("Price"), rst.getString("Description"), rst.getDate("Date"));
    }


    @Override
    public boolean UpdateQty(OrderItem entity) throws SQLException {
        return SQLUtil.execute ( "Update Items set Quantity = Quantity - ? where Item_id = ?",
               entity.getQuantity(),
                entity.getItem_id());

    }

    @Override
    public List<String> getItemName() throws SQLException {
        List<String> itemName = new ArrayList<>();
        ResultSet rst = SQLUtil.execute(
                "SELECT Item_id from Items");
        while (rst.next()) {
            itemName.add(rst.getString(1));
        }
        return itemName;
    }


}
