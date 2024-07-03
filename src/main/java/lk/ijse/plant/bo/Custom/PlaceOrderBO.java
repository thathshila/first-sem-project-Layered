package lk.ijse.plant.bo.Custom;

import lk.ijse.plant.bo.SuperBO;
import lk.ijse.plant.dto.CustomerDTO;
import lk.ijse.plant.dto.ItemDTO;
import lk.ijse.plant.dto.OrderDTO;
import lk.ijse.plant.entity.PlaceOrder;

import java.sql.SQLException;
import java.util.List;

public interface PlaceOrderBO extends SuperBO {
    public CustomerDTO searchCustomer(String nic) throws SQLException, ClassNotFoundException;
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException;
    public boolean existItem(String code) throws SQLException, ClassNotFoundException ;
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException ;
    public List<ItemDTO> setItemName() throws SQLException,ClassNotFoundException;
   // public String generateOrderID() throws SQLException, ClassNotFoundException ;
    public boolean placeOrder(PlaceOrder dto) throws SQLException,ClassNotFoundException;
    public ItemDTO findItem(String code)throws SQLException, ClassNotFoundException;

  public   String generateNewOrderID() throws SQLException,ClassNotFoundException;
}
