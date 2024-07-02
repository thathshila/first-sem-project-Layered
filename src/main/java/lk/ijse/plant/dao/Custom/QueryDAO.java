package lk.ijse.plant.dao.Custom;

import lk.ijse.plant.dao.SuperDAO;
import lk.ijse.plant.entity.CustomEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {
    ArrayList<CustomEntity> searchOrder(String Order_id) throws SQLException,ClassNotFoundException;
}
