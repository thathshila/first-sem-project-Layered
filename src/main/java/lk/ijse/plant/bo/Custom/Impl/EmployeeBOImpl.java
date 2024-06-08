package lk.ijse.plant.bo.Custom.Impl;

import lk.ijse.plant.bo.Custom.EmployeeBO;
import lk.ijse.plant.dao.Custom.EmployeeDAO;
import lk.ijse.plant.dao.DAOFactory;
import lk.ijse.plant.dto.EmployeeDTO;
import lk.ijse.plant.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);

    @Override
    public List<EmployeeDTO> getAllEmployee() throws SQLException,ClassNotFoundException{
        List<EmployeeDTO> arrayList = new ArrayList<>();
        List<Employee> employees = employeeDAO.getAll();
        for(Employee e : employees){
            arrayList.add(new EmployeeDTO(e.getEmployee_id(),e.getEmployee_name(),e.getAddress(),e.getContact(),e.getDate(),e.getSalary(),e.getWorking_hours(),e.getAttendance(),e.getPosition(),e.getUser_id()));
        }
        return arrayList;
    }

    @Override
    public boolean addEmployee(EmployeeDTO dto) throws SQLException,ClassNotFoundException{
        return employeeDAO.add(new Employee(dto.getEmployee_id(), dto.getEmployee_name(), dto.getAddress(), dto.getContact(),dto.getDate(), dto.getSalary(), dto.getWorking_hours(), dto.getAttendance(), dto.getPosition(), dto.getUser_id()));
    }

    @Override
    public boolean updateEmployee(EmployeeDTO dto)throws SQLException,ClassNotFoundException{
        return employeeDAO.update(new Employee(dto.getEmployee_id(), dto.getEmployee_name(), dto.getAddress(), dto.getContact(),dto.getDate(), dto.getSalary(), dto.getWorking_hours(), dto.getAttendance(), dto.getPosition(), dto.getUser_id()));
    }

    @Override
    public boolean deleteEmployee(String id) throws SQLException,ClassNotFoundException{
        return employeeDAO.delete(id);
    }

    @Override
    public String generateNewEmployeeID() throws SQLException, ClassNotFoundException {
        return employeeDAO.generateNewID();
    }
}

