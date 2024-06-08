package lk.ijse.plant.dto.tm;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@ToString
@Getter
@Setter
@Data
@NoArgsConstructor
public class ItemTM {
    private String Item_id;
    private String Item_name;
    private int Quantity;
    private double Price;
    private String Description;
    private Date Date;
}
