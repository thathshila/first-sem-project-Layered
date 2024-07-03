package lk.ijse.plant.view;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemTM {
    private String ItemId;
    private String ItemName;
    private int Quantity;
    private double Price;
    private double Total;
    private JFXButton btnRemove;
}
