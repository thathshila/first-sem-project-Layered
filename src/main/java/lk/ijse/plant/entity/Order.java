package lk.ijse.plant.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    private String Order_id;
    private Date Date;
    private double Price;
    private String Customer_id;
    private String User_id;
}
