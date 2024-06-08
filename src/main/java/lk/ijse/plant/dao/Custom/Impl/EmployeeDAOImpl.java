package lk.ijse.plant.dao.Custom.Impl;

import lk.ijse.plant.dao.Custom.EmployeeDAO;
import lk.ijse.plant.dao.SQLUtil;
import lk.ijse.plant.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public List<Employee> getAll() throws SQLException, ClassNotFoundException {
        List<Employee> employees = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM Employee");
        while (rst.next()) {
            Employee employee = new Employee(rst.getString("Employee_id"), rst.getString("Employee_name"), rst.getString("Address"), rst.getInt("Contact"), rst.getDate("Date"), rst.getDouble("Salary"), rst.getString("Working_hours"), rst.getString("Attendance"), rst.getString("Position"), rst.getString("User_id"));
            employees.add(employee);
        }
        return employees;
    }

    @Override
    public boolean add(Employee entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO Employee(Employee_id,Employee_name,Address,Contact,Date,Salary,Working_hours,Attendance,Position,User_id) VALUES(?,?,?,?,?,?,?,?,?,?)",
                entity.getEmployee_id(),
                entity.getEmployee_name(),
                entity.getAddress(),
                entity.getContact(),
                entity.getDate(),
                entity.getSalary(),
                entity.getWorking_hours(),
                entity.getAttendance(),
                entity.getPosition(),
                entity.getUser_id());
    }

    @Override
    public boolean update(Employee entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE Employee SET Employee_name = ?,Address = ?,Contact = ?,Date = ?,Salary = ?,Working_hours = ?,Attendance = ?,Position = ?,User_id = ? WHERE Employee_id = ?",
                entity.getEmployee_id(),
                entity.getEmployee_name(),
                entity.getAddress(),
                entity.getContact(),
                entity.getDate(),
                entity.getSalary(),
                entity.getWorking_hours(),
                entity.getAttendance(),
                entity.getPosition(),
                entity.getUser_id());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM Employee WHERE Employee_id = ?", id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT Employee_id FROM Employee ORDER BY Employee_id DESC LIMIT 1");
        if (rst.next()) {
            String string = rst.getString(1);
            String[] strings = string.split("E0");
            int id = Integer.parseInt(strings[1]);
            id++;
            String ID = String.valueOf(id);
            int length = ID.length();
            if (length < 2) {
                return "E00" + id;
            } else {
                if (length < 3) {
                    return "E0" + id;
                } else {
                    return "E" + id;
                }
            }
        } else {
            return "E001";
        }
    }
}