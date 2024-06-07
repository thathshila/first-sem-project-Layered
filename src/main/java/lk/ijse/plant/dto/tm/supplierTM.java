package lk.ijse.plant.dto.tm;

import lombok.*;

import java.sql.Date;
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Data
public class supplierTM {
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
