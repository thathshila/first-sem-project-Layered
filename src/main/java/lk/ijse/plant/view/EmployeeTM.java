package lk.ijse.plant.view;

import lombok.*;

import java.sql.Date;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class EmployeeTM {
    private String Employee_id;
    private String Employee_name;
    private String Address;
    private int Contact;
    private Date Date;
    private Double Salary;
    private String Working_hours;
    private String Attendance;
    private String Position;
    private String User_id;
}
