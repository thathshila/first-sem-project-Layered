package lk.ijse.plant.dto;

import lombok.*;

import java.sql.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDTO {
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
