package lk.ijse.plant.dao.Custom.Impl;

import lk.ijse.plant.dao.Custom.CustomerDAO;
import lk.ijse.plant.dao.SQLUtil;
import lk.ijse.plant.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public List<Customer> getAll() throws SQLException, ClassNotFoundException {
        List<Customer> customers = new ArrayList<>();
        ResultSet rst = SQLUtil.execute(
                "SELECT * FROM Customer");
        while (rst.next()) {
            Customer customer = new Customer(rst.getString("Customer_id"), rst.getString("Customer_name"), rst.getInt("Contact"), rst.getString("Address"), rst.getString("Nic"), rst.getDate("Date"));
            customers.add(customer);
        }
        return customers;
    }

    public boolean add(Customer entity) throws SQLException ,ClassNotFoundException{
        return SQLUtil.execute(
                "INSERT INTO Customer(Customer_Id, Customer_Name, Contact, Address, Nic, Date) VALUES (?,?,?,?,?,?)",
        entity.getCustomer_id(),
        entity.getCustomer_name(),
        entity.getContact(),
        entity.getAddress(),
        entity.getNic(),
        entity.getDate());
    }

    @Override
    public boolean update(Customer entity) throws SQLException {
        return SQLUtil.execute(
                "UPDATE Customer SET Customer_Name = ?, Contact = ? , Address = ?, Nic = ?, Date = ?  WHERE Customer_Id = ?",
        entity.getCustomer_name(),
        entity.getContact(),
        entity.getAddress(),
        entity.getNic(),
        entity.getDate(),
        entity.getCustomer_id());
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute(
                "DELETE FROM Customer WHERE Customer_id = ?", id);
    }

    @Override
    public String generateNewID() throws SQLException {
        ResultSet rst = SQLUtil.execute(
                "SELECT Customer_id FROM Customer ORDER BY Customer_id DESC LIMIT 1");
        if(rst.next()){
            String string = rst.getString(1);
            String[] strings = string.split("C0");
            int id =Integer.parseInt(strings[1]);
            id++;
            String ID = String.valueOf(id);
            int length = ID.length();
            if(length < 2){
                return "C00"+id;
            }else {
                if(length < 3){
                    return "C0"+id;
                }else {
                    return "C"+id;
                }
            }
        }else {
            return "C001";
        }
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT Customer_id FROM Customer WHERE Customer_id=?", id);
        return rst.next();
    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Customer WHERE Nic=?");
        rst.next();
        return new Customer(rst.getString("Customer_id"), rst.getString("Customer_name"), rst.getInt("Contact"),rst.getString("Address"),rst.getString("Nic"),rst.getDate("date"));

    }

    @Override
    public List<String> getAllNic() throws SQLException {
        List<String> cusNicList = new ArrayList<>();
        ResultSet rst = SQLUtil.execute(
                "SELECT Nic FROM Customer");
        while (rst.next()) {
            cusNicList.add(rst.getString(1));
        }
        return cusNicList;
    }
}
