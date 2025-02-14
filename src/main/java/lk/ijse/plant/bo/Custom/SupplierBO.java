package lk.ijse.plant.bo.Custom;

import lk.ijse.plant.bo.SuperBO;
import lk.ijse.plant.dto.SupplierDTO;

import java.sql.SQLException;
import java.util.List;

public interface SupplierBO extends SuperBO {
    public List<SupplierDTO> getAllSupplier() throws SQLException,ClassNotFoundException;
    public boolean addSupplier(SupplierDTO dto) throws SQLException,ClassNotFoundException;
    public boolean updateSupplier(SupplierDTO dto) throws SQLException,ClassNotFoundException;
    public boolean deleteSupplier(String id) throws SQLException,ClassNotFoundException;
    public String generateNewSupplierID() throws SQLException,ClassNotFoundException;
}
