package lk.ijse.plant.view;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemTM {
    private String Item_id;
    private String Item_name;
    private int Quantity;
    private double Price;
    private double Total;
    private JFXButton btnRemove;


}
