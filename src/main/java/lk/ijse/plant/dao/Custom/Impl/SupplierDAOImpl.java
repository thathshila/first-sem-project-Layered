package lk.ijse.plant.dao.Custom.Impl;

import lk.ijse.plant.dao.Custom.SupplierDAO;
import lk.ijse.plant.dao.SQLUtil;
import lk.ijse.plant.dto.SupplierDTO;
import lk.ijse.plant.entity.Supplier;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {

   @Override
    public List<Supplier> getAll() throws SQLException,ClassNotFoundException{
        List<Supplier> suppliers = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM Supplier");
        while (rst.next()){
            Supplier supplier = new Supplier(rst.getString("Supplier_id"),rst.getString("Supplier_name"),rst.getString("Address"),rst.getInt("Contact"),rst.getInt("Quantity"),rst.getDouble("Price"),rst.getString("Product"),rst.getDate("Date"),rst.getString("NIC"));
            suppliers.add(supplier);
        }
        return suppliers;
    }

    @Override
    public boolean add(Supplier entity) throws SQLException,ClassNotFoundException{
        return SQLUtil.execute("INSERT INTO Supplier(Supplier_id,Supplier_name,Address,Contact,Quantity,Price,Product,Date,NIC) VALUES(?,?,?,?,?,?,?,?,?)",
            entity.getSupplier_id(),
            entity.getSupplier_name(),
            entity.getAddress(),
            entity.getContact(),
            entity.getQuantity(),
            entity.getPrice(),
            entity.getProduct(),
            entity.getDate(),
            entity.getNIC());
    }

    @Override
    public boolean update(Supplier entity) throws SQLException,ClassNotFoundException{
        return SQLUtil.execute("UPDATE Supplier SET Supplier_name = ?, Address = ?, Contact = ?, Quantity = ?, Price = ?, Product = ?, Date = ?, NIC = ? WHERE Supplier_id = ?",
                entity.getSupplier_id(),
                entity.getSupplier_name(),
                entity.getAddress(),
                entity.getContact(),
                entity.getQuantity(),
                entity.getPrice(),
                entity.getProduct(),
                entity.getDate(),
                entity.getNIC());
    }

    @Override
    public boolean delete(String id) throws SQLException,ClassNotFoundException{
        return SQLUtil.execute("DELETE FROM Supplier WHERE Supplier_id = ? ", id);
    }

    @Override
    public String generateNewID() throws SQLException,ClassNotFoundException{
        ResultSet rst = SQLUtil.execute("SELECT Supplier_id FROM Supplier ORDER BY Supplier_id DESC LIMIT 1;");
        if (rst.next()){
            String string=rst.getString(1);
            String[] strings = string.split("S0");
            int id = Integer.parseInt(strings[1]);
            id++;
            String ID = String.valueOf(id);
            int length = ID.length();
            if (length < 2){
                return "S00"+id;
            }else {
                if (length < 3){
                    return "S0"+id;
                }else {
                    return "S"+id;
                }
            }
        } else {
            return "S001";
        }
    }
}
