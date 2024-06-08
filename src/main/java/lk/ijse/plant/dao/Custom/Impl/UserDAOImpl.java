package lk.ijse.plant.dao.Custom.Impl;

import lk.ijse.plant.dao.Custom.UserDAO;
import lk.ijse.plant.dao.SQLUtil;
import lk.ijse.plant.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public List<User> getAll() throws SQLException, ClassNotFoundException {
        List<User> allUsers = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM User");
        while (rst.next()) {
            User user = new User(rst.getString("User_id"), rst.getString("User_Name"),rst.getDate("Date"),rst.getString("Password"));
            allUsers.add(user);
        }
        return allUsers;
    }

    @Override
    public boolean add(User entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO User(User_id , User_Name, User_Password,User_NIC, User_Email) VALUES(?,?,?,?,?)",entity.getUser_id(),entity.getUser_name(),entity.getDate(),entity.getPassword());
    }

    @Override
    public boolean update(User entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE User SET User_name = ?, Date = ?, Password = ? WHERE User_id = ?",entity.getUser_name(),entity.getDate(),entity.getPassword(),entity.getUser_id());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM User WHERE User_id = ?",id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT User_id FROM User ORDER BY User_id DESC LIMIT 1;");
        if (rst.next()) {
            String string=rst.getString(1);
            String[] strings = string.split("U0");
            int id = Integer.parseInt(strings[1]);
            id++;
            String ID = String.valueOf(id);
            int length = ID.length();
            if (length < 2){
                return "U00"+id;
            }else {
                if (length < 3){
                    return "U0"+id;
                }else {
                    return "U"+id;
                }
            }
        } else {
            return "U001";
        }
    }
}
