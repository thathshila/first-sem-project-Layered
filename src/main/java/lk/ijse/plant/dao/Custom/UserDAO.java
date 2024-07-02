package lk.ijse.plant.dao.Custom;

import lk.ijse.plant.dao.crudDAO;
import lk.ijse.plant.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO extends crudDAO<User> {
    List<String> getIds() throws SQLException;
}
