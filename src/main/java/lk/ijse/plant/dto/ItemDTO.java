package lk.ijse.plant.dto;

import lombok.*;

import java.sql.Date;
@AllArgsConstructor
@ToString
@Getter
@Setter
@Data
@NoArgsConstructor
public class ItemDTO {
    private String Item_id;
    private String Item_name;
    private int Quantity;
    private double Price;
    private String Description;
    private Date Date;

    public Object getQtyOnHand() {
        return Quantity;
    }

    public void setQtyOnHand(int i) {
        this.Quantity = i;
    }
}

