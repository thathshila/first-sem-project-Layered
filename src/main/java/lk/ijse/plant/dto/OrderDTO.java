package lk.ijse.plant.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO {
    private String Order_id;
    private Date Date;
    private double Price;
    private String Customer_id;
    private String User_id;

}

