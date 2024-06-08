package lk.ijse.plant.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class User {
    private   String User_id;
    private  String User_name;
    private Date Date;
    private String Password;
}
