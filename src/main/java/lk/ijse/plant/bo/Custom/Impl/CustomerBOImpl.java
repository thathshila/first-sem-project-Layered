package lk.ijse.plant.bo.Custom.Impl;

import lk.ijse.plant.bo.Custom.CustomerBO;
import lk.ijse.plant.dao.Custom.CustomerDAO;
import lk.ijse.plant.dao.DAOFactory;
import lk.ijse.plant.dto.CustomerDTO;
import lk.ijse.plant.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    @Override
    public List<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException{
        List<CustomerDTO> arrayList = new ArrayList<>();
        List<Customer> customers = customerDAO.getAll();
        for (Customer c : customers){
            arrayList.add(new CustomerDTO(c.getCustomer_id(),c.getCustomer_name(),c.getContact(),c.getAddress(),c.getNic(),c.getDate()));
        }
        return arrayList;
    }


    @Override
    public boolean addCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException{
        return customerDAO.add(new Customer(dto.getCustomer_id(), dto.getCustomer_name(), dto.getContact(), dto.getAddress(), dto.getNic(), dto.getDate()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException{
        return customerDAO.update(new Customer(dto.getCustomer_id(),dto.getCustomer_name(), dto.getContact(), dto.getAddress(), dto.getNic(), dto.getDate()));
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException{
        return customerDAO.delete(id);
    }

    @Override
    public String generateNewCustomerID() throws SQLException, ClassNotFoundException{
        return customerDAO.generateNewID();
    }

}
