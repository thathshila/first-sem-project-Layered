package lk.ijse.plant.bo.Custom.Impl;

import lk.ijse.plant.bo.Custom.UserBO;
import lk.ijse.plant.dao.Custom.UserDAO;
import lk.ijse.plant.dao.DAOFactory;
import lk.ijse.plant.dto.UserDTO;
import lk.ijse.plant.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public List<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException {
        List<UserDTO> allUsers= new ArrayList<>();
        List<User> all = userDAO.getAll();
        for (User u : all) {
            allUsers.add(new UserDTO(u.getUser_id(),u.getUser_name(),u.getDate(),u.getPassword()));
        }
        return allUsers;
    }

    @Override
    public boolean addUser(UserDTO dto) throws SQLException, ClassNotFoundException {
        return userDAO.add(new User(dto.getUser_id(),dto.getUser_name(),dto.getDate(),dto.getPassword()));
    }

    @Override
    public boolean updateUser(UserDTO dto) throws SQLException, ClassNotFoundException {
        return userDAO.update(new User(dto.getUser_id(),dto.getUser_name(),dto.getDate(),dto.getPassword()));
    }

    @Override
    public boolean deleteUser(String id) throws SQLException, ClassNotFoundException {
        return userDAO.delete(id);
    }

    @Override
    public String generateNewUserID() throws SQLException, ClassNotFoundException {
        return userDAO.generateNewID();
    }
}
