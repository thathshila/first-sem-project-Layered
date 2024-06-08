package lk.ijse.plant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private   String User_id;
    private  String User_name;
    private Date Date;
    private String Password;
}
