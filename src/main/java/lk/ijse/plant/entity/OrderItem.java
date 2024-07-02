package lk.ijse.plant.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItem {
    private String Order_id;
    private String Item_id;
    private int Quantity;
    private double Price;
}
