package lk.ijse.plant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO implements Serializable {
    private String Order_id;
    private String Item_id;
    private int Quantity;
    private double Price;

}
