package lk.ijse.plant.bo.Custom.Impl;

import lk.ijse.plant.bo.Custom.SupplierBO;
import lk.ijse.plant.dao.Custom.SupplierDAO;
import lk.ijse.plant.dao.DAOFactory;
import lk.ijse.plant.dto.SupplierDTO;
import lk.ijse.plant.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierBOImpl  implements SupplierBO {
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);

    @Override
    public List<SupplierDTO> getAllSupplier() throws SQLException,ClassNotFoundException{
        List<SupplierDTO> allSupplier = new ArrayList<>();
        List<Supplier> all = supplierDAO.getAll();
        for (Supplier s : all){
            allSupplier.add(new SupplierDTO(s.getSupplier_id(),s.getSupplier_name(),s.getAddress(),s.getContact(),s.getQuantity(),s.getPrice(),s.getProduct(),s.getDate(),s.getNIC()));
        }
        return allSupplier;
    }

    @Override
    public boolean addSupplier(SupplierDTO dto) throws SQLException,ClassNotFoundException{
        return supplierDAO.add(new Supplier(dto.getSupplier_id(),dto.getSupplier_name(),dto.getAddress(),dto.getContact(),dto.getQuantity(),dto.getPrice(),dto.getProduct(),dto.getDate(),dto.getNIC()));
    }

    @Override
}
