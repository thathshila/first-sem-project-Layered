package lk.ijse.plant.bo.Custom.Impl;

import lk.ijse.plant.bo.Custom.ItemBO;
import lk.ijse.plant.dao.Custom.ItemDAO;
import lk.ijse.plant.dao.DAOFactory;
import lk.ijse.plant.dto.ItemDTO;
import lk.ijse.plant.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public List<ItemDTO> getAllItem() throws SQLException,ClassNotFoundException{
        List<ItemDTO> item = new ArrayList<>();
        List<Item> items = itemDAO.getAll();
        for(Item i : items){
            item.add(new ItemDTO(i.getItem_id(),i.getItem_name(),i.getQuantity(),i.getPrice(),i.getDescription(),i.getDate()));
        }
        return item;
    }
    @Override
    public boolean addItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.add(new Item(dto.getItem_id(),dto.getItem_name(),dto.getQuantity(),dto.getPrice(),dto.getDescription(),dto.getDate()));
    }

    @Override
    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(dto.getItem_id(),dto.getItem_name(),dto.getQuantity(),dto.getPrice(),dto.getDescription(),dto.getDate()));
    }

    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(id);
    }

    @Override
    public boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(code);
    }

    @Override
    public String generateNewItemID() throws SQLException, ClassNotFoundException {
        return itemDAO.generateNewID();
    }
}


