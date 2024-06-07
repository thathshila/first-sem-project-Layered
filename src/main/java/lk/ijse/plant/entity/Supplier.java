package lk.ijse.plant.entity;

import lombok.*;

import java.sql.Date;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Data
@Setter
public class Supplier {
    private String Supplier_id;
    private String Supplier_name;
    private String Address;
    private int Contact;
    private int Quantity;
    private double Price;
    private String Product;
    private Date Date;
    private String NIC;
}
