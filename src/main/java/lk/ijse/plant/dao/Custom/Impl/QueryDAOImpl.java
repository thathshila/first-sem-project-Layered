package lk.ijse.plant.dao.Custom.Impl;

import lk.ijse.plant.dao.Custom.QueryDAO;
import lk.ijse.plant.dao.SQLUtil;
import lk.ijse.plant.entity.CustomEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public ArrayList<CustomEntity> searchOrder(String Order_id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT Orders.Order_id,Orders.Date,Orders.Customer_id,Order_Item.Order_id,Order_Item.Item_id,Order_Item.Quantity,Order_Item.Price FROM Order INNER JOIN ON Orders.Order_id = Order_Item.Order_id WHERE Orders.Order_id = ? ", Order_id);
        ArrayList<CustomEntity> allRecords= new ArrayList<>();
        while (rst.next()) {
            String OrderId = rst.getString("Order_id");
            String date = rst.getString("Date");
            String Customer_id = rst.getString("Customer_id");
            String Item_id = rst.getString("Item_id");
            int Quantity = rst.getInt("Quantity");
            double Price = rst.getDouble("Price");

            CustomEntity customEntity = new CustomEntity(OrderId,date,Customer_id,Item_id,Quantity,Price);
            allRecords.add(customEntity);
        }
        return allRecords;
    }
}
