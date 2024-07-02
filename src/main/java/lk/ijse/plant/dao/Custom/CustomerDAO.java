package lk.ijse.plant.dao.Custom;

import lk.ijse.plant.dao.crudDAO;
import lk.ijse.plant.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO extends crudDAO<Customer> {

    List<String> getAllNic() throws SQLException;
}
