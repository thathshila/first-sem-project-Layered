package lk.ijse.plant.dao;

import java.sql.SQLException;
import java.util.List;

public interface crudDAO <T> extends SuperDAO{
    public List<T> getAll() throws SQLException,ClassNotFoundException;

    public boolean add(T entity) throws SQLException, ClassNotFoundException;

    public  boolean update(T entity) throws SQLException,ClassNotFoundException;

    public boolean delete(String id) throws SQLException,ClassNotFoundException;

    public String generateNewID() throws SQLException, ClassNotFoundException;
}
